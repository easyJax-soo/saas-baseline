package com.baseline.mybatis.handler;

import com.baseline.mybatis.Interceptor.HierarchyTenantLineInnerInterceptor;
import com.baseline.utils.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认层级租户处理器实现
 * 通过SecurityUtils获取用户登录时预计算的层级租户ID列表
 *
 * @author csy
 * @since 2025-11-17
 */
@Slf4j
@Component
public class DefaultHierarchyTenantHandler implements HierarchyTenantLineInnerInterceptor.HierarchyTenantHandler {

    @Override
    public List<Long> getTenantAndChildrenIds(Long tenantId) {
        log.info("DefaultHierarchyTenantHandler 被调用，租户ID: {}", tenantId);
        
        if (tenantId == null) {
            log.warn("租户ID为null，返回空列表");
            return new ArrayList<>();
        }

        try {
            // 从SecurityUtils获取用户登录时预计算的层级租户ID列表
            List<Long> hierarchyTenantIds = SecurityUtils.getHierarchyTenantIds();
            log.info("从SecurityUtils获取到的层级租户ID: {}", hierarchyTenantIds);
            
            if (hierarchyTenantIds != null && !hierarchyTenantIds.isEmpty()) {
                log.info("返回预计算的层级租户ID列表: {}", hierarchyTenantIds);
                return hierarchyTenantIds;
            }
            
            // 如果没有层级租户ID列表，则只返回当前租户ID
            List<Long> fallbackResult = new ArrayList<>();
            fallbackResult.add(tenantId);
            log.warn("没有找到层级租户ID列表，降级返回当前租户ID: {}", fallbackResult);
            return fallbackResult;
            
        } catch (Exception e) {
            log.warn("获取层级租户ID失败，租户ID: {}, 错误: {}", tenantId, e.getMessage());
            // 发生异常时，至少返回当前租户ID
            List<Long> fallbackResult = new ArrayList<>();
            fallbackResult.add(tenantId);
            return fallbackResult;
        }
    }
}
