package com.baseline.utils.cache;

import cn.dev33.satoken.SaManager;
import com.baseline.common.constant.CommonConstants;

import java.util.List;

/**
 * 权限缓存工具类
 * 用于清除用户权限、角色和项目权限相关的缓存
 */
public class PermissionCacheUtils {

    /**
     * 清除单个用户的权限、角色和项目权限缓存
     * @param userId 用户ID
     */
    public static void clearUserCache(Long userId) {
        if (userId == null) {
            return;
        }
        
        try {
            // 清除角色缓存
            SaManager.getSaTokenDao().deleteObject(CommonConstants.SATOKEN_ROLE_CACHE_PREFIX + userId);
            
            // 清除权限缓存
            SaManager.getSaTokenDao().deleteObject(CommonConstants.SATOKEN_PERMISSION_CACHE_PREFIX + userId);
            
            // 清除项目权限缓存（与权限缓存保持一致的逻辑）
            SaManager.getSaTokenDao().deleteObject(CommonConstants.SATOKEN_PROJECT_CACHE_PREFIX + userId);
            
        } catch (Exception e) {
            // 忽略清除缓存的异常
        }
    }

    /**
     * 批量清除多个用户的权限、角色和项目权限缓存
     * @param userIds 用户ID列表
     */
    public static void clearUserCacheBatch(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return;
        }
        
        for (Long userId : userIds) {
            clearUserCache(userId);
        }
    }

    /**
     * 根据角色ID清除所有拥有该角色的用户缓存
     * 注意：这个方法需要查询数据库获取用户列表，性能开销较大
     * 建议在角色权限变更不频繁的场景使用
     * @param roleId 角色ID
     */
    public static void clearCacheByRoleId(Long roleId) {
        // 这里需要查询拥有该角色的所有用户
        // 由于没有直接的Service依赖，这个方法留给具体的Service实现类调用
        // 可以在SysRoleServiceImpl中实现具体逻辑
    }
    
    /**
     * 清除用户在指定租户下的缓存
     * 注意：由于项目权限缓存与权限缓存保持一致，只使用用户ID，
     * 所以这个方法实际上与 clearUserCache 效果相同
     * @param userId 用户ID
     * @param tenantId 租户ID（保留参数以保持API兼容性）
     */
    public static void clearUserTenantCache(Long userId, Long tenantId) {
        // 直接调用通用的用户缓存清理方法
        clearUserCache(userId);
    }

    /**
     * 根据租户ID清除该租户下所有用户的缓存
     * 注意：这个方法需要查询数据库获取用户列表，性能开销较大
     * 建议在租户权限变更不频繁的场景使用
     * @param tenantId 租户ID
     */
    public static void clearCacheByTenantId(Long tenantId) {
        // 这里需要查询该租户下的所有用户
        // 由于没有直接的Service依赖，这个方法留给具体的Service实现类调用
        // 可以在SysTenantPermissionServiceImpl等中实现具体逻辑
    }

}
