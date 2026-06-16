package com.baseline.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.common.dto.UserRoleBizDTO;
import com.baseline.common.vo.UserRoleBizVO;
import com.baseline.utils.security.SecurityUtils;
import com.baseline.utils.cache.PermissionCacheUtils;
import com.baseline.core.exception.BusinessException;
import com.baseline.system.dto.SysRoleDetailDTO;
import com.baseline.system.dto.SysRoleFilterDTO;
import com.baseline.system.dto.SysRoleSaveDTO;
import com.baseline.system.entity.*;
import com.baseline.system.mapper.SysRoleMapper;
import com.baseline.system.service.*;
import com.baseline.system.vo.SimpleRoleVO;
import com.baseline.system.vo.SysRolePageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysRoleMenuService roleMenuService;

    @Autowired
    private ISysRolePermissionService rolePermissionService;

    @Autowired
    private ISysUserRoleService userRoleService;

    @Autowired
    private ISysRoleDeptService roleDeptService;

    @Autowired
    private ISysRoleProjectService roleProjectService;

    /**
     * 新增编辑系统角色
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public boolean saveOrUpdateRole(SysRoleSaveDTO dto) {
        Long roleId;
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);

        // 判断key是否重复
        if (lambdaQuery()
                .ne(dto.getId() != null, SysRole::getId, dto.getId())
                .eq(SysRole::getKey, dto.getKey()).exists()) {
            throw new BusinessException(dto.getKey() + ",key已经存在");
        }

        if (dto.getId() == null) {
            save(sysRole);
            roleId = sysRole.getId();
        } else {
            sysRole.setUpdateTime(LocalDateTime.now());
            baseMapper.updateRole(sysRole);
            roleId = dto.getId();
        }

        // 角色菜单关联
        roleMenuService.lambdaUpdate()
                .eq(SysRoleMenu::getRoleId, dto.getId())
                .remove();
        List<SysRoleMenu> roleMenus = new ArrayList<>();
        if (!CollectionUtil.isEmpty(dto.getMenuIds())) {
            for (Long menuId : dto.getMenuIds()) {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenus.add(roleMenu);
            }
        }
        roleMenuService.saveBatch(roleMenus);

        // 角色权限关联
        rolePermissionService.lambdaUpdate()
                .eq(SysRolePermission::getRoleId, dto.getId())
                .remove();
        List<SysRolePermission> rolePermissions = new ArrayList<>();
        if (!CollectionUtil.isEmpty(dto.getPermissionIds())) {
            for (Long permissionId : dto.getPermissionIds()) {
                SysRolePermission rolePermission = new SysRolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                rolePermissions.add(rolePermission);
            }
            rolePermissionService.saveBatch(rolePermissions);
        }

        // 清除拥有该角色的所有用户的权限缓存
        clearUserCacheByRoleId(roleId);

        // 角色部门关联
        roleDeptService.lambdaUpdate()
                .eq(SysRoleDept::getRoleId, roleId)
                .remove();
        if (!CollectionUtil.isEmpty(dto.getDeptIds())) {
            List<SysRoleDept> depts = new ArrayList<>();
            for (Long deptId : dto.getDeptIds()) {
                SysRoleDept roleDept = new SysRoleDept();
                roleDept.setRoleId(roleId);
                roleDept.setDeptId(deptId);
                depts.add(roleDept);
            }
            roleDeptService.saveBatch(depts);
        }

        // 角色项目关联
        if (!CollectionUtil.isEmpty(dto.getProjectCodes())) {
            roleProjectService.assignProjectsToRole(roleId, dto.getProjectCodes());
        }

        return true;
    }

    /**
     * 系统角色详情
     *
     * @return
     */
    @Override
    public SysRoleSaveDTO detail(SysRoleDetailDTO dto) {
        SysRoleSaveDTO result = new SysRoleSaveDTO();
        SysRole role = lambdaQuery()
                .eq(SysRole::getId, dto.getId())
                .eq(SysRole::getDeleted, false)
                .one();
        if (role != null) {
            BeanUtils.copyProperties(role, result);
            List<SysRoleMenu> roleMenus = roleMenuService.lambdaQuery()
                    .eq(SysRoleMenu::getRoleId, dto.getId())
                    .list();
            if (!CollectionUtil.isEmpty(roleMenus)) {
                result.setMenuIds(roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList()));
            }

            List<SysRolePermission> permissionMenus = rolePermissionService.lambdaQuery()
                    .eq(SysRolePermission::getRoleId, dto.getId())
                    .list();
            if (!CollectionUtil.isEmpty(permissionMenus)) {
                result.setPermissionIds(permissionMenus.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList()));
            }

            List<SysRoleDept> roleDepts = roleDeptService.lambdaQuery()
                    .eq(SysRoleDept::getRoleId, dto.getId())
                    .list();
            if (!CollectionUtil.isEmpty(roleDepts)) {
                result.setDeptIds(roleDepts.stream().map(SysRoleDept::getDeptId).collect(Collectors.toList()));
            }

            // 查询角色项目关联
            List<String> projectCodes = roleProjectService.getProjectCodesByRoleId(dto.getId());
            result.setProjectCodes(projectCodes);
        }

        return result;
    }

    /**
     * 删除系统角色
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public boolean removeSysRole(List<Long> ids) {
        // 先清除拥有这些角色的用户缓存
        for (Long roleId : ids) {
            clearUserCacheByRoleId(roleId);
        }
        
        lambdaUpdate()
                .in(SysRole::getId, ids)
                .eq(SysRole::getDeleted, false)
                .set(SysRole::getDeleted, 1)
                .update();
        roleMenuService.lambdaUpdate()
                .in(SysRoleMenu::getRoleId, ids)
                .remove();
        roleDeptService.lambdaUpdate()
                .in(SysRoleDept::getRoleId, ids)
                .remove();
        roleProjectService.lambdaUpdate()
                .in(SysRoleProject::getRoleId, ids)
                .remove();
        return true;
    }

    @Override
    public List<SimpleRoleVO> getSimpleList() {
        return baseMapper.getSimpleList(SecurityUtils.getTenantId());
    }

    @Override
    public List<SimpleRoleVO> getSimpleListByUserId(Long userId) {
        return baseMapper.getSimpleListByUserId(userId);
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public List<SysRole> selectUserRoleByUserId(Long userId)
    {
        return baseMapper.selectUserRoleByUserId(userId);
    }

    @Override
    public List<UserRoleBizVO> getRolesByUserId(UserRoleBizDTO dto) {
        List<SysRole> sysRole = selectUserRoleByUserId(dto.getUserId());
        return BeanUtil.copyToList(sysRole, UserRoleBizVO.class);
    }

    @Override
    public IPage<SysRolePageVO> pageRole(SysRoleFilterDTO dto) {
        Page<SysRolePageVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return baseMapper.page(page, dto);
    }

    /**
     * 根据角色ID清除拥有该角色的所有用户的权限缓存
     * @param roleId 角色ID
     */
    private void clearUserCacheByRoleId(Long roleId) {
        try {
            // 查询拥有该角色的所有用户ID
            List<Long> userIds = userRoleService.lambdaQuery()
                    .eq(SysUserRole::getRoleId, roleId)
                    .list()
                    .stream()
                    .map(SysUserRole::getUserId)
                    .collect(Collectors.toList());
            
            // 批量清除用户缓存
            PermissionCacheUtils.clearUserCacheBatch(userIds);
        } catch (Exception e) {
            // 忽略清除缓存的异常
        }
    }


}
