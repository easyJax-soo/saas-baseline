package com.baseline.common.service.impl;

import com.baseline.common.dto.SysOperLogBizDTO;
import com.baseline.common.service.ISysLogBizService;
import com.baseline.common.service.factory.RemoteLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 系统日志业务服务 Feign 实现
 *
 * @author system
 */
@FeignClient(contextId = "remoteLogService", value = "system-service", fallbackFactory = RemoteLogFallbackFactory.class)
public interface SysLogBizServiceImpl extends ISysLogBizService {

    /**
     * 保存系统操作日志
     */
    @PostMapping("/feignApi/log/saveOperLog")
    @Override
    boolean saveOperLog(@RequestBody SysOperLogBizDTO sysOperLogBizDTO, @RequestHeader("from-source") String source);
}
