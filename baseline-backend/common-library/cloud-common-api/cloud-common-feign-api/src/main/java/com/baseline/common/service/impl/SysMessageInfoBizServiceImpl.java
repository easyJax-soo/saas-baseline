package com.baseline.common.service.impl;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.SysMessageInfoSaveTypeBizDTO;
import com.baseline.common.service.ISysMessageInfoBizService;
import com.baseline.common.service.factory.RemoteTenantFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author bryant
 * @date 2025/11/25
 **/
@FeignClient(contextId = "SysMessageInfoBizService", value = "system-service", fallbackFactory = RemoteTenantFallbackFactory.class)
public interface SysMessageInfoBizServiceImpl extends ISysMessageInfoBizService {
    @Override
    @PostMapping("/feignApi/sysMessageInfo/saveByType")
    boolean saveByType(@RequestBody SysMessageInfoSaveTypeBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
