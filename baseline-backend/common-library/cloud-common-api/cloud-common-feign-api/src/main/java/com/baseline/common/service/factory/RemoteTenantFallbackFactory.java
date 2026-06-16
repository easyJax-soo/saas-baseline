package com.baseline.common.service.factory;

import com.baseline.common.dto.SysTenantDetailBizDTO;
import com.baseline.common.dto.SysTenantFilterBizDTO;
import com.baseline.common.dto.TenantHierarchyBizDTO;
import com.baseline.common.service.ISysTenantBizService;
import com.baseline.common.vo.SysTenantBizVO;
import com.baseline.common.vo.SysTenantTreeBizVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 租户服务降级工厂
 *
 * @author cascade
 * @date 2025/11/17
 */
@Component
public class RemoteTenantFallbackFactory implements FallbackFactory<ISysTenantBizService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteTenantFallbackFactory.class);

    @Override
    public ISysTenantBizService create(Throwable throwable) {
        log.error("租户服务调用失败:{}", throwable.getMessage());
        
        return new ISysTenantBizService() {
            @Override
            public List<Long> getTenantAndChildrenIds(TenantHierarchyBizDTO dto, String source) {
                log.error("获取层级租户ID失败:{}", throwable.getMessage());
                // 降级策略：只返回当前租户ID
                Long tenantId = dto != null ? dto.getTenantId() : null;
                return tenantId != null ? Arrays.asList(tenantId) : Collections.emptyList();
            }

            @Override
            public List<SysTenantBizVO> getTenantList(SysTenantFilterBizDTO dto, String source) {
                log.error("获取租户列表失败:{}", throwable.getMessage());
                // 降级策略：返回空列表
                return Collections.emptyList();
            }

            @Override
            public List<SysTenantTreeBizVO> getTenantTree(SysTenantFilterBizDTO dto, String source) {
                log.error("获取租户树形结构失败:{}", throwable.getMessage());
                // 降级策略：返回空列表
                return Collections.emptyList();
            }

            @Override
            public SysTenantBizVO getTenantDetail(SysTenantDetailBizDTO dto, String source) {
                log.error("获取租户详情失败:{}", throwable.getMessage());
                // 降级策略：返回null
                return null;
            }
        };
    }
}
