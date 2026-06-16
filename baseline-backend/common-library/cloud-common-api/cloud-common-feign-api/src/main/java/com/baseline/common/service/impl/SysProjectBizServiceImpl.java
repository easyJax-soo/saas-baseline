package com.baseline.common.service.impl;

import com.baseline.common.service.ISysProjectBizService;
import com.baseline.common.service.factory.RemoteProjectFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 系统项目权限业务服务 Feign 实现
 *
 * @author system
 */
@FeignClient(contextId = "remoteProjectService", value = "system-service", fallbackFactory = RemoteProjectFallbackFactory.class)
public interface SysProjectBizServiceImpl extends ISysProjectBizService {

    /**
     * 获取当前用户有权限访问的项目编码列表
     */
    @PostMapping("/feignApi/project/getUserProjectCodes")
    @Override
    List<String> getUserProjectCodes(@RequestHeader("from-source") String source);
}
