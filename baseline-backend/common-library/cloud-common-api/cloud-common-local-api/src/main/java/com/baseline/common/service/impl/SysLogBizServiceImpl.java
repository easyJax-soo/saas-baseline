package com.baseline.common.service.impl;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.SysOperLogBizDTO;
import com.baseline.common.service.ISysLogBizService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import jakarta.annotation.Resource;

/**
 * 系统日志业务服务本地实现
 * 用于单体模式下的本地调用
 */
@Service
public class SysLogBizServiceImpl implements ISysLogBizService {

    @Resource(name = "sysOplogServiceImpl")
    private Object sysOplogService;

    @Override
    public boolean saveOperLog(@RequestBody SysOperLogBizDTO sysOperLogBizDTO, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        try {
            java.lang.reflect.Method method = sysOplogService.getClass().getMethod("saveOperLog", SysOperLogBizDTO.class);
            return (Boolean) method.invoke(sysOplogService, sysOperLogBizDTO);
        } catch (Exception e) {
            throw new RuntimeException("调用系统日志服务失败", e);
        }
    }
}
