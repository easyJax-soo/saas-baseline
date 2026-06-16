package com.baseline.common.service.impl;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.SysTenantDetailBizDTO;
import com.baseline.common.dto.SysTenantFilterBizDTO;
import com.baseline.common.dto.TenantHierarchyBizDTO;
import com.baseline.common.service.ISysTenantBizService;
import com.baseline.common.service.factory.RemoteTenantFallbackFactory;
import com.baseline.common.vo.SysTenantBizVO;
import com.baseline.common.vo.SysTenantTreeBizVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 租户服务Feign客户端
 *
 * @author cascade
 * @date 2025/11/17
 */
@FeignClient(contextId = "sysTenantBizService", value = "system-service", fallbackFactory = RemoteTenantFallbackFactory.class)
public interface SysTenantBizServiceImpl extends ISysTenantBizService {

    @Override
    @PostMapping("/feignApi/tenant/children")
    List<Long> getTenantAndChildrenIds(@RequestBody TenantHierarchyBizDTO dto, 
                                       @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @PostMapping("/feignApi/tenant/list")
    List<SysTenantBizVO> getTenantList(@RequestBody SysTenantFilterBizDTO dto,
                                       @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @PostMapping("/feignApi/tenant/tree")
    List<SysTenantTreeBizVO> getTenantTree(@RequestBody SysTenantFilterBizDTO dto,
                                           @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @PostMapping("/feignApi/tenant/detail")
    SysTenantBizVO getTenantDetail(@RequestBody SysTenantDetailBizDTO dto,
                                   @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
