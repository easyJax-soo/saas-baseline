package com.baseline.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.system.dto.*;
import com.baseline.system.entity.SysTenantMenu;
import com.baseline.system.entity.SysTenantUser;
import com.baseline.system.mapper.SysTenantMenuMapper;
import com.baseline.system.mapper.SysTenantUserMapper;
import com.baseline.system.service.ISysTenantMenuService;
import com.baseline.system.vo.SysTenantMenuNodeVO;
import com.baseline.system.vo.SysTenantMenuVO;
import com.baseline.utils.cache.PermissionCacheUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 租户菜单表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Service
public class SysTenantMenuServiceImpl extends ServiceImpl<SysTenantMenuMapper, SysTenantMenu> implements ISysTenantMenuService {

    @Autowired
    private SysTenantUserMapper tenantUserMapper;

    @Override
    public List<SysTenantMenuNodeVO> getTenantAdminNodeList(SysTenantMenuFilterDTO dto) {
        List<SysTenantMenuVO> list = selectTenantMenuList(dto);
        return buildMenuNode(list, null);
    }


    /**
     * 获取菜单列表
     * @param dto
     * @return
     */
    private List<SysTenantMenuVO> selectTenantMenuList(SysTenantMenuFilterDTO dto){
        return baseMapper.selectTenantMenu();
    }


    private List<SysTenantMenuNodeVO> buildMenuNode(List<SysTenantMenuVO> menuList, List<Long> filterIds) {
        menuList.sort(Comparator.comparingInt(SysTenantMenuVO::getLevel).thenComparing(SysTenantMenuVO::getSortNo).reversed());
        List<SysTenantMenuNodeVO> sysTenantMenuNodeVOList = menuList.stream().map(menu -> {
            SysTenantMenuNodeVO node = new SysTenantMenuNodeVO();
            BeanUtils.copyProperties(menu, node);
            node.setChildren(new ArrayList<>());
            return node;
        }).collect(Collectors.toList());

        for (int i = 0; i < sysTenantMenuNodeVOList.size(); i++) {
            SysTenantMenuNodeVO node = sysTenantMenuNodeVOList.get(i);
            SysTenantMenuNodeVO parent = sysTenantMenuNodeVOList.stream()
                    .filter(v -> v.getId().equals(node.getParentId())).findFirst().orElse(null);

            if (CollectionUtil.isNotEmpty(filterIds)) {
                if ((filterIds.contains(node.getId()) || CollectionUtil.isNotEmpty(node.getChildren()))) {
                    if (parent != null) {
                        parent.getChildren().add(node);
                        sysTenantMenuNodeVOList.remove(node);
                        i--;
                    }
                } else {
                    sysTenantMenuNodeVOList.remove(node);
                    i--;
                }
            } else {
                if (parent != null) {
                    parent.getChildren().add(node);
                    sysTenantMenuNodeVOList.remove(node);
                    i--;
                }
            }

        }
        sortByNo(sysTenantMenuNodeVOList);
        return sysTenantMenuNodeVOList;
    }


    private void sortByNo(List<SysTenantMenuNodeVO> sysTenantMenuNodeVOList) {
        sysTenantMenuNodeVOList.sort(Comparator.comparingInt(SysTenantMenuNodeVO::getSortNo));
        for (SysTenantMenuNodeVO node : sysTenantMenuNodeVOList) {
            if (CollectionUtil.isNotEmpty(node.getChildren())) {
                sortByNo(node.getChildren());
            }
        }
    }

    @Override
    public boolean saveOrUpdate(SysTenantMenuSaveDTO dto) {
        super.lambdaUpdate()
                .eq(SysTenantMenu::getTenantId, dto.getTenantId())
                .remove();

        if(ObjectUtil.isNotEmpty(dto.getMenuIds())){
            dto.getMenuIds().forEach(menuId -> {
                SysTenantMenu entity = new SysTenantMenu();
                entity.setTenantId(dto.getTenantId());
                entity.setMenuId(menuId);
                baseMapper.insert(entity);
            });
        }
        
        // 清除该租户下所有用户的权限缓存
        clearTenantUserCache(dto.getTenantId());
        
        return true;
    }


    @Override
    public List<Long> getDetailByTenantId(Long tenantId) {
        // 使用忽略租户拦截器的方法，手动传递tenantId
        // 这样系统管理员可以查询任意指定租户的菜单资源
        List<SysTenantMenuVO> tenantMenuList = baseMapper.selectTenantMenuByTenantId(tenantId);
        return tenantMenuList.stream().map(SysTenantMenuVO::getId).collect(Collectors.toList());
    }

    @Override
    public boolean remove(SysTenantMenuRemoveDTO dto) {
        LambdaQueryWrapper<SysTenantMenu> query = new LambdaQueryWrapper<>();
        query.eq(SysTenantMenu::getTenantId, dto.getTenantId());
        query.in(SysTenantMenu::getMenuId, dto.getMenuIds());
        int res = baseMapper.delete(query);
        
        // 清除该租户下所有用户的权限缓存
        if (res > 0) {
            clearTenantUserCache(dto.getTenantId());
        }
        
        return res > 0;
    }
    
    /**
     * 清除租户下所有用户的权限缓存
     * @param tenantId 租户ID
     */
    private void clearTenantUserCache(Long tenantId) {
        try {
            // 直接使用Mapper查询，避免循环依赖
            LambdaQueryWrapper<SysTenantUser> tenantUserQuery = new LambdaQueryWrapper<>();
            tenantUserQuery.select(SysTenantUser::getUserId)
                    .eq(SysTenantUser::getTenantId, tenantId);
            List<SysTenantUser> tenantUsers = tenantUserMapper.selectList(tenantUserQuery);
            
            List<Long> userIds = tenantUsers.stream()
                    .map(SysTenantUser::getUserId)
                    .collect(Collectors.toList());
            
            // 批量清除用户缓存
            PermissionCacheUtils.clearUserCacheBatch(userIds);
        } catch (Exception e) {
            // 忽略清除缓存的异常
        }
    }

}
