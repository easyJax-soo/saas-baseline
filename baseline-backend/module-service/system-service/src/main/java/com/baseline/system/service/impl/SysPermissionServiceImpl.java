package com.baseline.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.common.dto.UserPermissionBizDTO;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.common.vo.UserPermissionBizVO;
import com.baseline.core.exception.BusinessException;
import com.baseline.system.dto.SysPermissionFilterDTO;
import com.baseline.system.dto.SysPermissionSaveDTO;
import com.baseline.system.entity.SysPermission;
import com.baseline.system.entity.SysRolePermission;
import com.baseline.system.entity.SysUserRole;
import com.baseline.system.entity.SysTenantPermission;
import com.baseline.system.entity.SysTenantUser;
import com.baseline.system.mapper.SysPermissionMapper;
import com.baseline.system.mapper.SysTenantUserMapper;
import com.baseline.system.service.*;
import com.baseline.system.vo.*;
import com.baseline.utils.security.SecurityUtils;
import com.baseline.utils.cache.PermissionCacheUtils;
import com.baseline.utils.tree.TreeUtils;
import org.springframework.stereotype.Service;

/**
 * 用户权限处理
 * 
 * @author ruoyi
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    // 注入需要的Service来查询相关用户
    private ISysRolePermissionService rolePermissionService;
    private ISysUserRoleService userRoleService;
    private ISysTenantPermissionService tenantPermissionService;
    private SysTenantUserMapper tenantUserMapper;

    // 构造函数注入（避免循环依赖）
    public SysPermissionServiceImpl(ISysRolePermissionService rolePermissionService,
                                   ISysUserRoleService userRoleService,
                                   ISysTenantPermissionService tenantPermissionService,
                                   SysTenantUserMapper tenantUserMapper) {
        this.rolePermissionService = rolePermissionService;
        this.userRoleService = userRoleService;
        this.tenantPermissionService = tenantPermissionService;
        this.tenantUserMapper = tenantUserMapper;
    }

    @Override
    public List<SysPermissionNodeVO> getNodeList(SysPermissionFilterDTO dto) {
        LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(dto.getName()), SysPermission::getName, dto.getName())
                .eq(StrUtil.isNotBlank(dto.getProjectCode()), SysPermission::getProjectCode, dto.getProjectCode());

        List<SysPermission> sysPermissionList = baseMapper.selectList(queryWrapper);

        if (sysPermissionList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> filterIds = null;

//        Long userId = SecurityUtils.getUserId();
//        LoginUserVO sysUser = SecurityUtils.getLoginUser();
//        if (!SecurityUtils.isAdmin(userId)) {
//            List<SysPermission> myNodeList = null;
//            if (sysUser.getSysUserLoginVO().isTenant() && sysUser.getSysUserLoginVO().getTenantInfo().getIsTenantAdmin()) {
//                //租户管理员直接返回所有的菜单
//                List<Long> permissionIds = Optional.ofNullable(
//                        sysTenantPermissionService.lambdaQuery().eq(SysTenantPermission::getTenantId, SecurityUtils.getTenantId()).list()
//                ).orElse(new ArrayList<>()).stream().map(SysTenantPermission::getPermissionId).collect(Collectors.toList());
//                myNodeList = baseMapper.selectBatchIds(permissionIds);
//            } else {
//                // 非管理员只返回有权限的菜单
//                myNodeList = baseMapper.selectPermsByUserId(userId);
//            }
//            if (CollectionUtil.isNotEmpty(myNodeList)) {
//                filterIds = myNodeList.stream().map(SysPermission::getId).collect(Collectors.toList());
//            }
//        }
        return TreeUtils.buildTreeWithBeanCopy(sysPermissionList, SysPermissionNodeVO.class, filterIds);
    }



    @Override
    public boolean saveOrUpdate(SysPermissionSaveDTO dto) {
        SysPermission entity = BeanUtil.copyProperties(dto, SysPermission.class);

        // 检查是否是更新操作且父ID发生了变化
        boolean isUpdate = entity.getId() != null;
        Long oldParentId = null;
        if (isUpdate) {
            SysPermission oldEntity = baseMapper.selectById(entity.getId());
            oldParentId = oldEntity != null ? oldEntity.getParentId() : null;
        }

        // 计算层级
        if (entity.getParentId() == null || entity.getParentId() == 0) {
            entity.setLevel(1);
        } else {
            // 防止循环引用：不能将自己设置为父节点
            if (entity.getId() != null && entity.getId().equals(entity.getParentId())) {
                throw new BusinessException("不能将自己设置为父节点");
            }
            
            SysPermission parentEntity = baseMapper.selectById(entity.getParentId());
            if (ObjectUtil.isNull(parentEntity)) {
                throw new BusinessException("上级数据未找到");
            }
            
            // 防止循环引用：检查是否会形成循环
            if (entity.getId() != null && isCircularReference(entity.getId(), entity.getParentId())) {
                throw new BusinessException("不能将子节点设置为父节点，会造成循环引用");
            }
            
            entity.setLevel(parentEntity.getLevel() + 1);
        }

        boolean result = saveOrUpdate(entity);
        
        // 只有在更新操作且父ID发生变化时，才需要更新子节点的层级
        if (result && isUpdate && !Objects.equals(oldParentId, entity.getParentId())) {
            updateChildrenLevel(entity);
        }
        
        // 清除相关用户的权限缓存
        if (result) {
            clearPermissionRelatedCache(entity.getId());
        }
        
        return result;
    }
    
    /**
     * 递归更新子节点的层级
     */
    private void updateChildrenLevel(SysPermission parentPermission) {
        List<SysPermission> children = lambdaQuery()
                .eq(SysPermission::getParentId, parentPermission.getId())
                .list();
        
        if (CollectionUtil.isEmpty(children)) {
            return;
        }
        
        for (SysPermission child : children) {
            // 更新子节点的层级
            child.setLevel(parentPermission.getLevel() + 1);
            
            // 更新数据库
            updateById(child);
            
            // 递归更新子节点的子节点
            updateChildrenLevel(child);
        }
    }
    
    /**
     * 检查是否存在循环引用
     */
    private boolean isCircularReference(Long nodeId, Long parentId) {
        if (parentId == null || parentId == 0) {
            return false;
        }
        
        // 如果父节点ID等于当前节点ID，则存在循环引用
        if (nodeId.equals(parentId)) {
            return true;
        }
        
        // 递归检查父节点的父节点
        SysPermission parent = baseMapper.selectById(parentId);
        if (parent != null && parent.getParentId() != null) {
            return isCircularReference(nodeId, parent.getParentId());
        }
        
        return false;
    }

    @Override
    public boolean safeRemoveByIds(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return true;
        }
        
        // 检查每个要删除的权限是否有子节点
        for (Long id : ids) {
            List<SysPermission> children = lambdaQuery()
                    .eq(SysPermission::getParentId, id)
                    .list();
            
            if (CollectionUtil.isNotEmpty(children)) {
                SysPermission permission = baseMapper.selectById(id);
                String permissionName = permission != null ? permission.getName() : "ID:" + id;
                throw new BusinessException("权限【" + permissionName + "】存在子权限，不能删除");
            }
        }
        
        // 如果所有权限都没有子节点，则可以安全删除
        boolean result = removeByIds(ids);
        
        // 清除相关用户的权限缓存
        if (result) {
            for (Long id : ids) {
                clearPermissionRelatedCache(id);
            }
        }
        
        return result;
    }

    @Override
    public SysPermissionDetailVO getById(Long id) {
        SysPermission entity = super.getById(id);
        SysPermissionDetailVO vo = BeanUtil.copyProperties(entity, SysPermissionDetailVO.class);
        return vo;
    }


    /**
     * 获取菜单数据
     * 
     * @return 菜单权限信息
     */
    @Override
    public List<String> getPermissionByUserId(Long userId)
    {
        List<SysPermission> permission = baseMapper.selectPermsByUserId(userId);
        return permission.stream().map(SysPermission::getPermission).collect(Collectors.toList());
    }

    @Override
    public List<UserPermissionBizVO> getPermissionsByUserIdAndRoleId(UserPermissionBizDTO dto) {
        // 获取租户信息
        Long tenantId = SecurityUtils.getTenantId();
        Boolean isTenantAdmin = SecurityUtils.isTenantAdmin();
        
        // 1. 如果不是租户环境且是超级管理员，返回所有权限
        if ((tenantId == null || tenantId == 0)) {
            LoginUserBizVO loginUser = SecurityUtils.getLoginUser();
            if (SecurityUtils.isAdmin(loginUser)) {
                // 超级管理员拥有所有权限，这里可以返回所有权限或者特殊标识
                return getAllPermissions();
            }
        }
        
        // 2. 如果roleId和roleIds都为null，说明是获取用户所有权限
        if (dto.getRoleId() == null && (dto.getRoleIds() == null || dto.getRoleIds().isEmpty())) {
            if (tenantId != null && tenantId > 0 && isTenantAdmin != null && isTenantAdmin) {
                // 租户管理员：获取租户分配的所有权限
                return getTenantAdminPermissions(dto.getUserId());
            } else {
                // 其他情况：获取用户通过所有角色拥有的权限
                return getUserAllPermissions(dto.getUserId());
            }
        }
        
        // 3. 如果有具体的角色ID（单个或多个），根据角色ID获取权限
        return getPermissionsByUserIdAndRoleIds(dto);
    }
    
    /**
     * 根据用户ID和角色ID（单个或多个）获取权限
     */
    private List<UserPermissionBizVO> getPermissionsByUserIdAndRoleIds(UserPermissionBizDTO dto) {
        // 如果只有单个roleId，转换为roleIds列表
        if (dto.getRoleId() != null) {
            List<Long> roleIds = new ArrayList<>();
            roleIds.add(dto.getRoleId());
            dto.setRoleIds(roleIds);
        }
        
        // 如果roleIds为空，返回空列表
        if (dto.getRoleIds() == null || dto.getRoleIds().isEmpty()) {
            return new ArrayList<>();
        }
        
        // 调用支持多角色的mapper方法
        return baseMapper.getPermissionsByUserIdAndRoleIds(dto);
    }
    
    /**
     * 获取所有权限（超级管理员）
     */
    private List<UserPermissionBizVO> getAllPermissions() {
        // 这里可以返回所有权限或者特殊标识
        // 根据业务需求，可能需要查询所有权限或返回特殊标识
        List<UserPermissionBizVO> allPermissions = new ArrayList<>();
        UserPermissionBizVO superAdminPermission = new UserPermissionBizVO();
        superAdminPermission.setPermission("*:*:*");
        allPermissions.add(superAdminPermission);
        return allPermissions;
    }
    
    /**
     * 获取租户管理员权限
     */
    private List<UserPermissionBizVO> getTenantAdminPermissions(Long userId) {
        // 租户管理员应该拥有该租户分配的所有权限
        // MyBatis-Plus会自动根据当前租户ID过滤sys_tenant_permission表
        return baseMapper.getTenantAdminPermissions(userId);
    }
    
    /**
     * 获取用户通过所有角色拥有的权限
     */
    private List<UserPermissionBizVO> getUserAllPermissions(Long userId) {
        // 使用现有的selectPermsByUserId方法，然后转换为UserPermissionBizVO
        List<SysPermission> permissions = baseMapper.selectPermsByUserId(userId);
        return permissions.stream().map(permission -> {
            UserPermissionBizVO vo = new UserPermissionBizVO();
            vo.setPermission(permission.getPermission());
            return vo;
        }).collect(Collectors.toList());
    }
    
    /**
     * 清除与权限相关的用户缓存
     * @param permissionId 权限ID
     */
    private void clearPermissionRelatedCache(Long permissionId) {
        try {
            Set<Long> affectedUserIds = new HashSet<>();
            
            // 1. 查询通过角色拥有该权限的用户
            List<Long> roleIds = rolePermissionService.lambdaQuery()
                    .select(SysRolePermission::getRoleId)
                    .eq(SysRolePermission::getPermissionId, permissionId)
                    .list()
                    .stream()
                    .map(SysRolePermission::getRoleId)
                    .collect(Collectors.toList());
            
            if (!roleIds.isEmpty()) {
                List<Long> userIds = userRoleService.lambdaQuery()
                        .select(SysUserRole::getUserId)
                        .in(SysUserRole::getRoleId, roleIds)
                        .list()
                        .stream()
                        .map(SysUserRole::getUserId)
                        .collect(Collectors.toList());
                affectedUserIds.addAll(userIds);
            }
            
            // 2. 查询通过租户权限拥有该权限的用户
            List<Long> tenantIds = tenantPermissionService.lambdaQuery()
                    .select(SysTenantPermission::getTenantId)
                    .eq(SysTenantPermission::getPermissionId, permissionId)
                    .list()
                    .stream()
                    .map(SysTenantPermission::getTenantId)
                    .collect(Collectors.toList());
            
            if (!tenantIds.isEmpty()) {
                for (Long tenantId : tenantIds) {
                    // 直接使用Mapper查询，避免循环依赖
                    LambdaQueryWrapper<SysTenantUser> tenantUserQuery = new LambdaQueryWrapper<>();
                    tenantUserQuery.select(SysTenantUser::getUserId)
                            .eq(SysTenantUser::getTenantId, tenantId);
                    List<SysTenantUser> tenantUsers = tenantUserMapper.selectList(tenantUserQuery);
                    
                    List<Long> tenantUserIds = tenantUsers.stream()
                            .map(SysTenantUser::getUserId)
                            .collect(Collectors.toList());
                    affectedUserIds.addAll(tenantUserIds);
                }
            }
            
            // 3. 批量清除受影响用户的缓存
            if (!affectedUserIds.isEmpty()) {
                PermissionCacheUtils.clearUserCacheBatch(new ArrayList<>(affectedUserIds));
            }
            
        } catch (Exception e) {
            // 忽略清除缓存的异常
        }
    }
}
