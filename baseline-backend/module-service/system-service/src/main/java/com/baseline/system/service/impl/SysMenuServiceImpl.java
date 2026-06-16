package com.baseline.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.core.exception.BusinessException;
import com.baseline.system.dto.SysMenuFilterDTO;
import com.baseline.system.dto.SysMenuQueryDTO;
import com.baseline.system.dto.SysMenuSaveDTO;
import com.baseline.system.entity.SysMenu;
import com.baseline.system.mapper.SysMenuMapper;
import com.baseline.system.service.ISysMenuService;
import com.baseline.system.vo.SysMenuNodeVO;
import com.baseline.system.vo.SysMenuVO;
import com.baseline.utils.security.SecurityUtils;
import com.baseline.utils.tree.TreeUtils;
import org.springframework.stereotype.Service;
import com.baseline.common.constant.CommonConstants;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {


    @Override
    public List<SysMenuNodeVO> getNodeList(SysMenuFilterDTO dto) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(dto.getName()), SysMenu::getName, dto.getName())
                .eq(dto.getStatus() != null, SysMenu::getStatus, dto.getStatus())
                .eq(StrUtil.isNotBlank(dto.getProjectCode()), SysMenu::getProjectCode, dto.getProjectCode());
//                .ne(SysMenu::getType, "F");

        //租户管理员直接返回所有的菜单
//        SysUserLoginVO sysUser = SecurityUtils.getLoginUser().getSysUserLoginVO();
//        if (sysUser.isTenant() && sysUser.getTenantInfo().getIsTenantAdmin()) {
//            List<Long> menuIds = Optional.ofNullable(
//                    sysTenantMenuService.lambdaQuery().eq(SysTenantMenu::getTenantId, SecurityUtils.getTenantId()).list()
//            ).orElse(new ArrayList<>()).stream().map(SysTenantMenu::getMenuId).collect(Collectors.toList());
//            queryWrapper.in(SysMenu::getId, menuIds);
//        }

        List<SysMenu> sysMenuList = baseMapper.selectList(queryWrapper);
        if (sysMenuList.isEmpty()) {
            return new ArrayList<>();
        }
        return TreeUtils.buildTreeWithBeanCopy(sysMenuList, SysMenuNodeVO.class, null);
    }

    @Override
    public List<SysMenuVO> getBtnNodeList(Long id) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId, id)
                .eq(SysMenu::getType, "F");

        List<SysMenu> sysMenuList = baseMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(sysMenuList, SysMenuVO.class);
    }


    @Override
    public List<SysMenuNodeVO> getMyNodeList(SysMenuQueryDTO dto) {
        String projectCode = dto != null ? dto.getProjectCode() : null;
        
        LoginUserBizVO sysUser = SecurityUtils.getLoginUser();
        Long userId = SecurityUtils.getUserId();
        Long tenantId = SecurityUtils.getTenantId();
        Boolean isTenantAdmin = SecurityUtils.isTenantAdmin();

        List<SysMenu> list;
        
        // 如果是租户用户，需要过滤租户菜单
        if (tenantId != null && tenantId > 0) {
            // 租户用户：只获取租户授权的菜单
            list = baseMapper.getTenantMenuList().stream()
                    .filter(menu -> !"F".equals(menu.getType()) && menu.getStatus() == CommonConstants.SYS_ENABLE)
                    .filter(menu -> StrUtil.isBlank(projectCode) || projectCode.equals(menu.getProjectCode()))
                    .collect(Collectors.toList());
        } else {
            // 非租户用户：获取所有启用的菜单
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getStatus, CommonConstants.SYS_ENABLE)
                    .ne(SysMenu::getType, "F");
            if (StrUtil.isNotBlank(projectCode)) {
                queryWrapper.eq(SysMenu::getProjectCode, projectCode);
            }
            list = this.list(queryWrapper);
        }

        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> filterIds = null;
        
        // 超级管理员直接返回所有菜单
        if (SecurityUtils.isAdmin(sysUser)) {
            return TreeUtils.buildTreeWithBeanCopy(list, SysMenuNodeVO.class, null);
        }
        
        // 租户管理员返回租户分配的所有菜单
        if (tenantId != null && tenantId > 0 && isTenantAdmin != null && isTenantAdmin) {
            return TreeUtils.buildTreeWithBeanCopy(list, SysMenuNodeVO.class, null);
        }
        
        // 普通用户（包括普通租户用户）需要根据角色权限过滤菜单
        List<SysMenu> myNodeList = null;
        if (tenantId != null && tenantId > 0) {
            // 普通租户用户：从sys_role_menu表中获取该租户下用户的权限菜单
            if (StrUtil.isNotBlank(projectCode)) {
                myNodeList = baseMapper.getMyTenantRoleNodeListByProject(userId, tenantId, projectCode, null);
            } else {
                myNodeList = baseMapper.getMyTenantRoleNodeList(userId, tenantId, null);
            }
        } else {
            // 非租户普通用户：获取用户的权限菜单
            if (StrUtil.isNotBlank(projectCode)) {
                myNodeList = baseMapper.getMyNodeListByProject(userId, projectCode, null);
            } else {
                myNodeList = baseMapper.getMyNodeList(userId, null);
            }
        }
        
        if (CollectionUtil.isNotEmpty(myNodeList)) {
            filterIds = myNodeList.stream().map(SysMenu::getId).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
        
        return TreeUtils.buildTreeWithBeanCopy(list, SysMenuNodeVO.class, filterIds);
    }

    @Override
    public List<String> getBtnPermission(Long userId, Boolean isAdmin) {
        // 超级管理员直接返回全部权限标识
        if (isAdmin) {
            List<String> permList = new ArrayList<>();
            permList.add("*:*:*");
            return permList;
        }
        
        Long tenantId = SecurityUtils.getTenantId();
        Boolean isTenantAdmin = SecurityUtils.isTenantAdmin();
        List<SysMenu> list;
        
        // 如果是租户用户，需要过滤租户菜单
        if (tenantId != null && tenantId > 0) {
            // 租户用户：只获取租户授权的按钮菜单
            list = baseMapper.getTenantMenuList().stream()
                    .filter(menu -> "F".equals(menu.getType()) && menu.getStatus() == CommonConstants.SYS_ENABLE)
                    .collect(Collectors.toList());
        } else {
            // 非租户用户：获取所有启用的按钮菜单
            list = lambdaQuery().eq(SysMenu::getType, "F").eq(SysMenu::getStatus, CommonConstants.SYS_ENABLE).list();
        }

        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 租户管理员返回租户分配的所有按钮权限
        if (tenantId != null && tenantId > 0 && isTenantAdmin != null && isTenantAdmin) {
            return list.stream().map(SysMenu::getKey).collect(Collectors.toList());
        }
        
        // 普通用户（包括普通租户用户）需要根据角色权限过滤按钮
        List<SysMenu> myNodeList = null;
        if (tenantId != null && tenantId > 0) {
            // 普通租户用户：从sys_role_menu表中获取该租户下用户的权限按钮
            myNodeList = baseMapper.getMyTenantRoleNodeList(userId, tenantId, "F");
        } else {
            // 非租户普通用户：获取用户的权限按钮
            myNodeList = baseMapper.getMyNodeList(userId, "F");
        }
        
        if (CollectionUtil.isNotEmpty(myNodeList)) {
            final List<Long> finalFilterIds = myNodeList.stream().map(SysMenu::getId).collect(Collectors.toList());
            // 根据filterIds过滤按钮权限
            return list.stream()
                    .filter(menu -> finalFilterIds.contains(menu.getId()))
                    .map(SysMenu::getKey)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }


    @Override
    public List<String> getMyBtnPermission(SysMenuQueryDTO dto) {
        String projectCode = dto != null ? dto.getProjectCode() : null;
        
        LoginUserBizVO sysUser = SecurityUtils.getLoginUser();
        Long userId = SecurityUtils.getUserId();
        
        // 超级管理员直接返回全部权限标识
        if (SecurityUtils.isAdmin(sysUser)) {
            List<String> permList = new ArrayList<>();
            permList.add("*:*:*");
            return permList;
        }
        
        Long tenantId = SecurityUtils.getTenantId();
        Boolean isTenantAdmin = SecurityUtils.isTenantAdmin();
        List<SysMenu> list;
        
        // 如果是租户用户，需要过滤租户菜单
        if (tenantId != null && tenantId > 0) {
            // 租户用户：只获取租户授权的按钮菜单
            list = baseMapper.getTenantMenuList().stream()
                    .filter(menu -> "F".equals(menu.getType()) && menu.getStatus() == CommonConstants.SYS_ENABLE)
                    .filter(menu -> StrUtil.isBlank(projectCode) || projectCode.equals(menu.getProjectCode()))
                    .collect(Collectors.toList());
        } else {
            // 非租户用户：获取所有启用的按钮菜单
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getStatus, CommonConstants.SYS_ENABLE)
                    .eq(SysMenu::getType, "F");
            if (StrUtil.isNotBlank(projectCode)) {
                queryWrapper.eq(SysMenu::getProjectCode, projectCode);
            }
            list = this.list(queryWrapper);
        }

        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        // 租户管理员返回租户分配的所有按钮权限
        if (tenantId != null && tenantId > 0 && isTenantAdmin != null && isTenantAdmin) {
            return list.stream()
                    .filter(menu -> StrUtil.isNotBlank(menu.getKey()))
                    .map(SysMenu::getKey)
                    .collect(Collectors.toList());
        }
        
        // 普通用户（包括普通租户用户）需要根据角色权限过滤按钮
        List<SysMenu> myNodeList = null;
        if (tenantId != null && tenantId > 0) {
            // 普通租户用户：从sys_role_menu表中获取该租户下用户的权限按钮
            if (StrUtil.isNotBlank(projectCode)) {
                myNodeList = baseMapper.getMyTenantRoleNodeListByProject(userId, tenantId, projectCode, "F");
            } else {
                myNodeList = baseMapper.getMyTenantRoleNodeList(userId, tenantId, "F");
            }
        } else {
            // 非租户普通用户：获取用户的权限按钮
            if (StrUtil.isNotBlank(projectCode)) {
                myNodeList = baseMapper.getMyNodeListByProject(userId, projectCode, "F");
            } else {
                myNodeList = baseMapper.getMyNodeList(userId, "F");
            }
        }
        
        if (CollectionUtil.isNotEmpty(myNodeList)) {
            return myNodeList.stream()
                    .filter(menu -> StrUtil.isNotBlank(menu.getKey()))
                    .map(SysMenu::getKey)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }


    @Override
    public boolean saveOrUpdate(SysMenuSaveDTO dto) {
        SysMenu entity = BeanUtil.copyProperties(dto, SysMenu.class);

        // 检查是否是更新操作且父ID发生了变化
        boolean isUpdate = entity.getId() != null;
        Long oldParentId = null;
        if (isUpdate) {
            SysMenu oldEntity = baseMapper.selectById(entity.getId());
            oldParentId = oldEntity != null ? oldEntity.getParentId() : null;
        }
        
        // 计算层级和父路径
        if (entity.getParentId() == null || entity.getParentId() == 0) {
            entity.setLevel(1);
            entity.setParentPath("0"); // 顶级节点的parentPath设置为"0"
        } else {
            // 防止循环引用：不能将自己设置为父节点
            if (entity.getId() != null && entity.getId().equals(entity.getParentId())) {
                throw new BusinessException("不能将自己设置为父节点");
            }
            
            SysMenu parentEntity = baseMapper.selectById(entity.getParentId());
            if (ObjectUtil.isNull(parentEntity)) {
                throw new BusinessException("上级菜单数据未找到");
            }
            
            // 防止循环引用：检查父节点的父路径中是否包含当前节点ID
            if (entity.getId() != null && StrUtil.isNotBlank(parentEntity.getParentPath())) {
                String[] parentPaths = parentEntity.getParentPath().split(",");
                for (String pathId : parentPaths) {
                    if (entity.getId().toString().equals(pathId)) {
                        throw new BusinessException("不能将子节点设置为父节点，会造成循环引用");
                    }
                }
            }
            
            // 设置层级为父级层级+1
            entity.setLevel(parentEntity.getLevel() + 1);
            
            // 构建父路径：父节点的parentPath + 父节点ID
            String parentPath = parentEntity.getParentPath();
            if (StrUtil.isNotBlank(parentPath)) {
                parentPath += ",";
            }
            parentPath += parentEntity.getId();
            entity.setParentPath(parentPath);
        }

        boolean result = saveOrUpdate(entity);
        
        // 只有在更新操作且父ID发生变化时，才需要更新子节点的层级和父路径
        if (result && isUpdate && !Objects.equals(oldParentId, entity.getParentId())) {
            updateChildrenLevelAndPath(entity);
        }
        
        return result;
    }
    
    /**
     * 递归更新子节点的层级和父路径
     */
    private void updateChildrenLevelAndPath(SysMenu parentMenu) {
        List<SysMenu> children = lambdaQuery()
                .eq(SysMenu::getParentId, parentMenu.getId())
                .list();
        
        if (CollectionUtil.isEmpty(children)) {
            return;
        }
        
        for (SysMenu child : children) {
            // 更新子节点的层级
            child.setLevel(parentMenu.getLevel() + 1);
            
            // 更新子节点的父路径：父节点的parentPath + 父节点ID
            String newParentPath = parentMenu.getParentPath();
            if (StrUtil.isNotBlank(newParentPath)) {
                newParentPath += ",";
            }
            newParentPath += parentMenu.getId();
            child.setParentPath(newParentPath);
            
            // 更新数据库
            updateById(child);
            
            // 递归更新子节点的子节点
            updateChildrenLevelAndPath(child);
        }
    }

    @Override
    public boolean safeRemoveByIds(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return true;
        }
        
        // 检查每个要删除的菜单是否有子节点
        for (Long id : ids) {
            List<SysMenu> children = lambdaQuery()
                    .eq(SysMenu::getParentId, id)
                    .list();
            
            if (CollectionUtil.isNotEmpty(children)) {
                SysMenu menu = baseMapper.selectById(id);
                String menuName = menu != null ? menu.getName() : "ID:" + id;
                throw new BusinessException("菜单【" + menuName + "】存在子菜单，不能删除");
            }
        }
        
        // 如果所有菜单都没有子节点，则可以安全删除
        return removeByIds(ids);
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public List<String> selectMenuPermsByUserId(Long userId) {
        return baseMapper.selectMenuPermsByUserId(userId);
    }


}
