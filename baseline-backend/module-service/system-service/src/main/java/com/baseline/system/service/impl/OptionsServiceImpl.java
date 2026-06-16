package com.baseline.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baseline.common.constant.CommonConstants;
import com.baseline.common.dto.OptionsQueryBizDTO;
import com.baseline.common.vo.TreeOptionVO;
import com.baseline.system.entity.*;
import com.baseline.system.service.*;
import com.baseline.system.vo.SysDeptNodeVO;
import com.baseline.system.vo.SysMenuNodeVO;
import com.baseline.system.vo.SysPermissionNodeVO;
import com.baseline.system.vo.SysTenantMenuVO;
import com.baseline.system.vo.SysTenantPermissionVO;
import com.baseline.system.mapper.SysTenantMenuMapper;
import com.baseline.system.mapper.SysTenantPermissionMapper;
import com.baseline.system.mapper.SysTenantUserMapper;
import com.baseline.system.mapper.SysTenantProjectMapper;
import com.baseline.system.dto.SysDeptFilterDTO;
import com.baseline.system.dto.SysMenuFilterDTO;
import com.baseline.system.dto.SysPermissionFilterDTO;
import com.baseline.utils.options.OptionsUtils;
import com.baseline.utils.security.SecurityUtils;
import com.baseline.utils.tree.TreeUtils;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 下拉选项服务实现
 * 
 * @author system
 */
@Service
public class OptionsServiceImpl {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;


    @Autowired
    private ISysPostService sysPostService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private ISysPermissionService sysPermissionService;

    @Autowired
    private ISysProjectService sysProjectService;


    @Autowired
    private SysTenantMenuMapper sysTenantMenuMapper;

    @Autowired
    private SysTenantPermissionMapper sysTenantPermissionMapper;

    @Autowired
    private SysTenantUserMapper sysTenantUserMapper;

    @Autowired
    private SysTenantProjectMapper sysTenantProjectMapper;


    public List<TreeOptionVO> getUserOptions(OptionsQueryBizDTO queryDTO) {
        Long tenantId = SecurityUtils.getTenantId();
        List<SysUser> userList;
        
        // 如果在租户环境下，直接使用 XML 查询方法（多租户拦截器会自动添加tenant_id条件）
        if (tenantId != null && tenantId > 0) {
            userList = sysTenantUserMapper.selectTenantUsers();
        } else {
            // 非租户环境：查询所有启用的用户
            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getStatus, CommonConstants.SYS_ENABLE)
                    .eq(SysUser::getDeleted, CommonConstants.SYS_NOT_DELETED);
            userList = sysUserService.list(queryWrapper);
        }
        
        // 关键词搜索过滤
        if (queryDTO != null && StrUtil.isNotBlank(queryDTO.getKeyword())) {
            String keyword = queryDTO.getKeyword();
            userList = userList.stream()
                    .filter(user -> user.getName().contains(keyword) || user.getAccount().contains(keyword))
                    .collect(Collectors.toList());
        }
        
        return OptionsUtils.fromEntityListToTreeOptions(userList, 
            user -> String.valueOf(user.getId()), 
            user -> user.getName());
    }

    public List<TreeOptionVO> getRoleOptions(OptionsQueryBizDTO queryDTO) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        
        // 默认只返回启用状态的数据
        queryWrapper.eq(SysRole::getStatus, CommonConstants.SYS_ENABLE)
                .eq(SysRole::getDeleted, CommonConstants.SYS_NOT_DELETED);
        
        // 注意：角色表包含 tenantId 字段，多租户拦截器会自动根据 tenant_id 过滤
        
        // 关键词搜索
        if (queryDTO != null && StrUtil.isNotBlank(queryDTO.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(SysRole::getName, queryDTO.getKeyword())
                .or()
                .like(SysRole::getKey, queryDTO.getKeyword())
            );
        }
        
        List<SysRole> roleList = sysRoleService.list(queryWrapper);
        return OptionsUtils.fromEntityListToTreeOptions(roleList, 
            role -> String.valueOf(role.getId()), 
            role -> role.getName());
    }


    public List<TreeOptionVO> getPostOptions(OptionsQueryBizDTO queryDTO) {
        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        
        // 默认只返回启用状态的数据
        queryWrapper.eq(SysPost::getStatus, CommonConstants.SYS_ENABLE)
                .eq(SysPost::getDeleted, CommonConstants.SYS_NOT_DELETED);
        
        // 注意：岗位表包含 tenantId 字段，多租户拦截器会自动根据 tenant_id 过滤
        
        // 关键词搜索
        if (queryDTO != null && StrUtil.isNotBlank(queryDTO.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(SysPost::getName, queryDTO.getKeyword())
                .or()
                .like(SysPost::getCode, queryDTO.getKeyword())
            );
        }
        
        List<SysPost> postList = sysPostService.list(queryWrapper);
        return OptionsUtils.fromEntityListToTreeOptions(postList, 
            post -> String.valueOf(post.getId()), 
            post -> post.getName());
    }

    public List<TreeOptionVO> getDeptOptions(OptionsQueryBizDTO queryDTO) {
        SysDeptFilterDTO filterDTO = new SysDeptFilterDTO();
        
        // 默认只查询启用状态的部门
        filterDTO.setStatus(CommonConstants.SYS_ENABLE);
        
        // 关键词搜索
        if (queryDTO != null && StrUtil.isNotBlank(queryDTO.getKeyword())) {
            filterDTO.setName(queryDTO.getKeyword());
        }
        
        // 注意：部门表包含 tenantId 字段，多租户拦截器会自动根据 tenant_id 过滤
        List<SysDeptNodeVO> deptNodeList = sysDeptService.getNodeList(filterDTO);
        return OptionsUtils.fromTreeNodeList(deptNodeList, 
            dept -> String.valueOf(dept.getId()), 
            dept -> dept.getName());
    }

    public List<TreeOptionVO> getMenuOptions(OptionsQueryBizDTO queryDTO) {
        Long tenantId = SecurityUtils.getTenantId();
        
        // 如果在租户环境下，直接使用现有的 XML 查询方法（多租户拦截器会自动添加tenant_id条件）
        if (tenantId != null && tenantId > 0) {
            List<SysTenantMenuVO> tenantMenuList = sysTenantMenuMapper.selectTenantMenu();
            
            // 关键词搜索过滤
            if (queryDTO != null && StrUtil.isNotBlank(queryDTO.getKeyword())) {
                String keyword = queryDTO.getKeyword();
                tenantMenuList = tenantMenuList.stream()
                        .filter(menu -> menu.getName().contains(keyword))
                        .collect(Collectors.toList());
            }
            
            // 项目编码过滤
            if (queryDTO != null && StrUtil.isNotBlank(queryDTO.getProjectCode())) {
                String projectCode = queryDTO.getProjectCode();
                tenantMenuList = tenantMenuList.stream()
                        .filter(menu -> projectCode.equals(menu.getProjectCode()))
                        .collect(Collectors.toList());
            }
            
            // 先构建树结构，然后转换为选项
            List<SysTenantMenuVO> treeMenuList = TreeUtils.buildTreeWithBeanCopy(tenantMenuList, SysTenantMenuVO.class, null);
            return OptionsUtils.fromTreeNodeList(treeMenuList,
                    menu -> String.valueOf(menu.getId()),
                    menu -> menu.getName());
        } else {
            // 非租户环境：使用原有的getNodeList方法
            SysMenuFilterDTO filterDTO = new SysMenuFilterDTO();
            
            // 默认只查询启用状态的菜单
            filterDTO.setStatus(CommonConstants.SYS_ENABLE);
            
            // 关键词搜索
            if (queryDTO != null && StrUtil.isNotBlank(queryDTO.getKeyword())) {
                filterDTO.setName(queryDTO.getKeyword());
            }
            
            // 项目编码过滤
            if (queryDTO != null && StrUtil.isNotBlank(queryDTO.getProjectCode())) {
                filterDTO.setProjectCode(queryDTO.getProjectCode());
            }
            
            List<SysMenuNodeVO> menuNodeList = sysMenuService.getNodeList(filterDTO);
            return OptionsUtils.fromTreeNodeList(menuNodeList, 
                menu -> String.valueOf(menu.getId()), 
                menu -> menu.getName());
        }
    }

    public List<TreeOptionVO> getPermissionOptions(OptionsQueryBizDTO queryDTO) {
        Long tenantId = SecurityUtils.getTenantId();
        
        // 如果在租户环境下，直接使用现有的 XML 查询方法（多租户拦截器会自动添加tenant_id条件）
        if (tenantId != null && tenantId > 0) {
            List<SysTenantPermissionVO> tenantPermissionList = sysTenantPermissionMapper.selectTenantPermission();
            
            // 关键词搜索过滤
            if (queryDTO != null && StrUtil.isNotBlank(queryDTO.getKeyword())) {
                String keyword = queryDTO.getKeyword();
                tenantPermissionList = tenantPermissionList.stream()
                        .filter(permission -> permission.getName().contains(keyword))
                        .collect(Collectors.toList());
            }
            
            // 项目编码过滤
            if (queryDTO != null && StrUtil.isNotBlank(queryDTO.getProjectCode())) {
                String projectCode = queryDTO.getProjectCode();
                tenantPermissionList = tenantPermissionList.stream()
                        .filter(permission -> projectCode.equals(permission.getProjectCode()))
                        .collect(Collectors.toList());
            }
            
            // 先构建树结构，然后转换为选项
            List<SysTenantPermissionVO> treePermissionList = TreeUtils.buildTreeWithBeanCopy(tenantPermissionList, SysTenantPermissionVO.class, null);
            return OptionsUtils.fromTreeNodeList(treePermissionList,
                    permission -> String.valueOf(permission.getId()),
                    permission -> permission.getName());
        } else {
            // 非租户环境：使用原有的getNodeList方法
            SysPermissionFilterDTO filterDTO = new SysPermissionFilterDTO();
            
            // 关键词搜索
            if (queryDTO != null && StrUtil.isNotBlank(queryDTO.getKeyword())) {
                filterDTO.setName(queryDTO.getKeyword());
            }
            
            // 项目编码过滤
            if (queryDTO != null && StrUtil.isNotBlank(queryDTO.getProjectCode())) {
                filterDTO.setProjectCode(queryDTO.getProjectCode());
            }
            
            List<SysPermissionNodeVO> permissionNodeList = sysPermissionService.getNodeList(filterDTO);
            return OptionsUtils.fromTreeNodeList(permissionNodeList, 
                permission -> String.valueOf(permission.getId()), 
                permission -> permission.getName());
        }
    }

    public List<TreeOptionVO> getProjectOptions(OptionsQueryBizDTO queryDTO) {
        Long tenantId = SecurityUtils.getTenantId();
        List<SysProject> projectList;
        
        // 如果在租户环境下，直接使用 XML 查询方法（多租户拦截器会自动添加tenant_id条件）
        if (tenantId != null && tenantId > 0) {
            projectList = sysTenantProjectMapper.selectTenantProjects();
        } else {
            // 非租户环境：查询所有启用的项目
            LambdaQueryWrapper<SysProject> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysProject::getStatus, CommonConstants.SYS_ENABLE)
                    .eq(SysProject::getDeleted, CommonConstants.SYS_NOT_DELETED)
                    .orderByAsc(SysProject::getSortNo);
            projectList = sysProjectService.list(queryWrapper);
        }
        
        // 关键词搜索过滤
        if (queryDTO != null && StrUtil.isNotBlank(queryDTO.getKeyword())) {
            String keyword = queryDTO.getKeyword();
            projectList = projectList.stream()
                    .filter(project -> project.getName().contains(keyword) || project.getCode().contains(keyword))
                    .collect(Collectors.toList());
        }
        
        return OptionsUtils.fromEntityListToTreeOptions(projectList, 
            project -> project.getCode(), 
            project -> project.getName());
    }

}
