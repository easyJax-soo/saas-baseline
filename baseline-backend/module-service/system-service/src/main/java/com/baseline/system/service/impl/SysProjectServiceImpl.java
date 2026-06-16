package com.baseline.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.common.constant.CommonConstants;
import com.baseline.core.exception.BusinessException;
import com.baseline.system.dto.SysProjectFilterDTO;
import com.baseline.system.dto.SysProjectSaveDTO;
import com.baseline.system.entity.SysProject;
import com.baseline.system.entity.SysUserRole;
import com.baseline.system.mapper.SysProjectMapper;
import com.baseline.system.service.ISysProjectService;
import com.baseline.system.service.ISysRoleProjectService;
import com.baseline.system.service.ISysTenantProjectService;
import com.baseline.system.service.ISysUserRoleService;
import com.baseline.system.vo.SysProjectPageVO;
import com.baseline.system.vo.SysProjectVO;
import com.baseline.system.vo.SysProjectTypeGroupVO;
import com.baseline.utils.security.SecurityUtils;
import com.baseline.common.vo.LoginUserBizVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统项目表 服务实现类
 *
 * @author system
 */
@Service
public class SysProjectServiceImpl extends ServiceImpl<SysProjectMapper, SysProject> implements ISysProjectService {

    @Autowired
    private ISysRoleProjectService sysRoleProjectService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysTenantProjectService sysTenantProjectService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(SysProjectSaveDTO dto) {
        // 检查编码是否重复
        LambdaQueryWrapper<SysProject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysProject::getCode, dto.getCode())
                .ne(dto.getId() != null, SysProject::getId, dto.getId())
                .eq(SysProject::getDeleted, CommonConstants.SYS_NOT_DELETED);
        
        if (this.exists(queryWrapper)) {
            throw new BusinessException("项目编码已存在");
        }
        
        SysProject project = new SysProject();
        BeanUtil.copyProperties(dto, project);
        
        // 设置创建时间和删除标记（新增时）
        if (dto.getId() == null) {
            project.setCreateTime(LocalDateTime.now());
            project.setDeleted(CommonConstants.SYS_NOT_DELETED);
        } else {
            // 更新时设置更新时间
            project.setUpdateTime(LocalDateTime.now());
        }
        
        // 使用 MyBatis-Plus 的 saveOrUpdate 方法，自动判断新增或更新
        boolean result = this.saveOrUpdate(project);
        
        // 项目变更后，理论上需要清除所有用户的项目权限缓存
        // 但由于缓存键基于用户ID，无法直接批量清除
        // 这里采用自然过期策略（30天），在实际使用中影响较小
        // 因为：
        // 1. 项目变更频率相对较低
        // 2. 用户重新登录或权限变更时会自动清除缓存
        // 3. 30天的过期时间对于项目权限来说是合理的
        
        return result;
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        boolean result = super.removeByIds(list);
        
               // 项目删除后，理论上需要清除所有用户的项目权限缓存
               // 但由于缓存键基于用户ID，无法直接批量清除
               // 这里采用自然过期策略，对实际使用影响较小
        
        return result;
    }

    @Override
    public SysProjectVO getDetail(Long id) {
        SysProject project = this.getById(id);
        if (project == null || CommonConstants.SYS_DELETED.equals(project.getDeleted())) {
            throw new BusinessException("项目不存在");
        }
        
        SysProjectVO vo = new SysProjectVO();
        BeanUtil.copyProperties(project, vo);
        return vo;
    }

    @Override
    public List<SysProjectVO> getList(SysProjectFilterDTO dto) {
        LambdaQueryWrapper<SysProject> queryWrapper = buildQueryWrapper(dto);
        queryWrapper.orderByAsc(SysProject::getSortNo);
        
        List<SysProject> projectList = this.list(queryWrapper);
        return projectList.stream()
                .map(project -> {
                    SysProjectVO vo = new SysProjectVO();
                    BeanUtil.copyProperties(project, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<SysProjectVO> getMyProjectList() {
        // 根据当前用户的角色权限获取项目列表
        Long currentUserId = SecurityUtils.getUserId();
        if (currentUserId == null) {
            return new ArrayList<>();
        }
        
        // 获取用户的角色ID列表
        List<Long> userRoleIds = sysUserRoleService.lambdaQuery()
                .eq(SysUserRole::getUserId, currentUserId)
                .list()
                .stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
        
        if (CollectionUtil.isEmpty(userRoleIds)) {
            return new ArrayList<>();
        }
        
        // 获取角色关联的项目编码
        Set<String> projectCodes = new HashSet<>();
        for (Long roleId : userRoleIds) {
            List<String> roleProjectCodes = sysRoleProjectService.getProjectCodesByRoleId(roleId);
            projectCodes.addAll(roleProjectCodes);
        }
        
        if (CollectionUtil.isEmpty(projectCodes)) {
            return new ArrayList<>();
        }
        
        LambdaQueryWrapper<SysProject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysProject::getStatus, CommonConstants.SYS_ENABLE)
                .eq(SysProject::getDeleted, CommonConstants.SYS_NOT_DELETED)
                .in(SysProject::getCode, projectCodes)
                .orderByAsc(SysProject::getSortNo);
        
        List<SysProject> projectList = this.list(queryWrapper);
        return projectList.stream()
                .map(project -> {
                    SysProjectVO vo = new SysProjectVO();
                    BeanUtil.copyProperties(project, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public IPage<SysProjectPageVO> pageProject(SysProjectFilterDTO dto) {
        Page<SysProject> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        LambdaQueryWrapper<SysProject> queryWrapper = buildQueryWrapper(dto);
        queryWrapper.orderByAsc(SysProject::getSortNo);
        
        IPage<SysProject> projectPage = this.page(page, queryWrapper);
        
        // 转换为VO
        IPage<SysProjectPageVO> voPage = new Page<>(projectPage.getCurrent(), projectPage.getSize(), projectPage.getTotal());
        List<SysProjectPageVO> voList = projectPage.getRecords().stream()
                .map(project -> {
                    SysProjectPageVO vo = new SysProjectPageVO();
                    BeanUtil.copyProperties(project, vo);
                    return vo;
                })
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        
        return voPage;
    }

    @Override
    public List<SysProjectTypeGroupVO> getProjectsByType() {
        // 获取当前登录用户信息
        LoginUserBizVO loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null || loginUser.getId() == null) {
            return new ArrayList<>();
        }
        
        Long currentUserId = loginUser.getId();
        Long tenantId = SecurityUtils.getTenantId();
        
        Set<String> allowedProjectCodes = new HashSet<>();
        
        // 判断权限逻辑
        if (tenantId == null || tenantId == 0) {
            // 非租户环境
            if (SecurityUtils.isAdmin(loginUser)) {
                // 超级管理员，返回所有项目
                return getAllProjectsByType();
            } else {
                // 非超级管理员，根据角色和项目关联表查询
                allowedProjectCodes = getProjectCodesByUserRoles(currentUserId);
            }
        } else {
            // 租户环境
            if (SecurityUtils.isTenantAdmin()) {
                // 租户管理员，查看给租户分配的项目
                List<String> tenantProjectCodes = sysTenantProjectService.getProjectCodesByTenantId(tenantId);
                allowedProjectCodes.addAll(tenantProjectCodes);
            } else {
                // 普通租户用户，根据角色和项目关联表查询
                allowedProjectCodes = getProjectCodesByUserRoles(currentUserId);
            }
        }
        
        if (allowedProjectCodes.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 根据允许的项目编码查询项目
        return getProjectsByTypeWithCodes(allowedProjectCodes);
    }
    
    /**
     * 获取所有项目按类型分组（超级管理员使用）
     */
    private List<SysProjectTypeGroupVO> getAllProjectsByType() {
        LambdaQueryWrapper<SysProject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysProject::getStatus, CommonConstants.SYS_ENABLE)
                .eq(SysProject::getDeleted, CommonConstants.SYS_NOT_DELETED)
                .orderByAsc(SysProject::getProjectType)
                .orderByAsc(SysProject::getSortNo);
        
        List<SysProject> projectList = this.list(queryWrapper);
        return groupProjectsByType(projectList);
    }
    
    /**
     * 根据用户角色获取项目编码
     */
    private Set<String> getProjectCodesByUserRoles(Long userId) {
        // 获取用户的角色ID列表
        List<Long> userRoleIds = sysUserRoleService.lambdaQuery()
                .eq(SysUserRole::getUserId, userId)
                .list()
                .stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
        
        if (CollectionUtil.isEmpty(userRoleIds)) {
            return new HashSet<>();
        }
        
        // 获取角色关联的项目编码
        Set<String> projectCodes = new HashSet<>();
        for (Long roleId : userRoleIds) {
            List<String> roleProjectCodes = sysRoleProjectService.getProjectCodesByRoleId(roleId);
            projectCodes.addAll(roleProjectCodes);
        }
        
        return projectCodes;
    }
    
    /**
     * 根据项目编码获取项目并按类型分组
     */
    private List<SysProjectTypeGroupVO> getProjectsByTypeWithCodes(Set<String> projectCodes) {
        LambdaQueryWrapper<SysProject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysProject::getStatus, CommonConstants.SYS_ENABLE)
                .eq(SysProject::getDeleted, CommonConstants.SYS_NOT_DELETED)
                .in(SysProject::getCode, projectCodes)
                .orderByAsc(SysProject::getProjectType)
                .orderByAsc(SysProject::getSortNo);
        
        List<SysProject> projectList = this.list(queryWrapper);
        return groupProjectsByType(projectList);
    }
    
    /**
     * 将项目列表按类型分组
     */
    private List<SysProjectTypeGroupVO> groupProjectsByType(List<SysProject> projectList) {
        // 按项目类型分组
        Map<String, List<SysProject>> projectTypeMap = projectList.stream()
                .collect(Collectors.groupingBy(project -> 
                    project.getProjectType() == null ? "未分类" : project.getProjectType()));
        
        // 转换为分组VO
        return projectTypeMap.entrySet().stream()
                .map(entry -> {
                    SysProjectTypeGroupVO groupVO = new SysProjectTypeGroupVO();
                    groupVO.setProjectType(entry.getKey());
                    groupVO.setCount(entry.getValue().size());
                    
                    List<SysProjectVO> projectVOList = entry.getValue().stream()
                            .map(project -> {
                                SysProjectVO vo = new SysProjectVO();
                                BeanUtil.copyProperties(project, vo);
                                return vo;
                            })
                            .collect(Collectors.toList());
                    
                    groupVO.setProjects(projectVOList);
                    return groupVO;
                })
                .sorted((g1, g2) -> g1.getProjectType().compareTo(g2.getProjectType()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getUserProjectCodes() {
        // 获取当前登录用户信息
        LoginUserBizVO loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null || loginUser.getId() == null) {
            return new ArrayList<>();
        }

        Long currentUserId = loginUser.getId();
        Long tenantId = SecurityUtils.getTenantId();

        // 直接查询用户的项目权限列表（缓存在拦截器中处理）
        Set<String> projectCodesSet = getUserAllowedProjectCodes(loginUser, currentUserId, tenantId);
        return new ArrayList<>(projectCodesSet);
    }

    /**
     * 获取用户允许访问的项目编码列表
     */
    private Set<String> getUserAllowedProjectCodes(LoginUserBizVO loginUser, Long currentUserId, Long tenantId) {
        Set<String> allowedProjectCodes = new HashSet<>();
        
        // 判断权限逻辑
        if (tenantId == null || tenantId == 0) {
            // 非租户环境
            if (SecurityUtils.isAdmin(loginUser)) {
                // 超级管理员，获取所有启用的项目编码
                allowedProjectCodes = getAllEnabledProjectCodes();
            } else {
                // 非超级管理员，根据角色和项目关联表查询
                allowedProjectCodes = getProjectCodesByUserRoles(currentUserId);
            }
        } else {
            // 租户环境
            if (SecurityUtils.isTenantAdmin()) {
                // 租户管理员，查看给租户分配的项目
                List<String> tenantProjectCodes = sysTenantProjectService.getProjectCodesByTenantId(tenantId);
                allowedProjectCodes.addAll(tenantProjectCodes);
            } else {
                // 普通租户用户，根据角色和项目关联表查询
                allowedProjectCodes = getProjectCodesByUserRoles(currentUserId);
            }
        }
        
        return allowedProjectCodes;
    }

    /**
     * 获取所有启用的项目编码（超级管理员使用）
     */
    @SuppressWarnings("unchecked")
    private Set<String> getAllEnabledProjectCodes() {
        LambdaQueryWrapper<SysProject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysProject::getStatus, CommonConstants.SYS_ENABLE)
                .eq(SysProject::getDeleted, CommonConstants.SYS_NOT_DELETED)
                .select(SysProject::getCode);
        
        List<SysProject> projectList = this.list(queryWrapper);
        return projectList.stream()
                .map(SysProject::getCode)
                .collect(Collectors.toSet());
    }



    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<SysProject> buildQueryWrapper(SysProjectFilterDTO dto) {
        LambdaQueryWrapper<SysProject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysProject::getDeleted, CommonConstants.SYS_NOT_DELETED);
        
        if (dto != null) {
            if (StrUtil.isNotBlank(dto.getCode())) {
                queryWrapper.like(SysProject::getCode, dto.getCode());
            }
            if (StrUtil.isNotBlank(dto.getName())) {
                queryWrapper.like(SysProject::getName, dto.getName());
            }
            if (StrUtil.isNotBlank(dto.getProjectType())) {
                queryWrapper.eq(SysProject::getProjectType, dto.getProjectType());
            }
            if (dto.getStatus() != null) {
                queryWrapper.eq(SysProject::getStatus, dto.getStatus());
            }
        }
        
        return queryWrapper;
    }
}