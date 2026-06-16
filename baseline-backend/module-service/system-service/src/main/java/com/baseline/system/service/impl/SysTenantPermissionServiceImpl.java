package com.baseline.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.system.dto.SysTenantPermissionSaveDTO;
import com.baseline.system.entity.SysTenantPermission;
import com.baseline.system.entity.SysTenantUser;
import com.baseline.system.mapper.SysTenantPermissionMapper;
import com.baseline.system.mapper.SysTenantUserMapper;
import com.baseline.system.service.ISysTenantPermissionService;
import com.baseline.system.vo.SysTenantPermissionVO;
import com.baseline.utils.cache.PermissionCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 租户权限表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Service
public class SysTenantPermissionServiceImpl extends ServiceImpl<SysTenantPermissionMapper, SysTenantPermission> implements ISysTenantPermissionService {

    @Autowired
    private SysTenantUserMapper tenantUserMapper;

    @Override
    public boolean saveOrUpdate(SysTenantPermissionSaveDTO dto) {
        super.lambdaUpdate()
                .eq(SysTenantPermission::getTenantId, dto.getTenantId())
                .remove();

        if(ObjectUtil.isNotEmpty(dto.getPermissionIds())){
            dto.getPermissionIds().forEach(permissionId -> {
                SysTenantPermission entity = new SysTenantPermission();
                entity.setTenantId(dto.getTenantId());
                entity.setPermissionId(permissionId);
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
        // 这样系统管理员可以查询任意指定租户的权限资源
        List<SysTenantPermissionVO> tenantPermissionList = baseMapper.selectTenantPermissionByTenantId(tenantId);
        return tenantPermissionList.stream().map(SysTenantPermissionVO::getId).collect(Collectors.toList());
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
