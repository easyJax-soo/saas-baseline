package com.baseline.common.service.impl;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.service.ISysProjectBizService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 系统项目权限业务服务本地实现
 * 用于单体模式下的本地调用
 */
@Service
public class SysProjectBizServiceImpl implements ISysProjectBizService {

    @Resource(name = "sysProjectServiceImpl")
    private Object sysProjectService;

    @Override
    public List<String> getUserProjectCodes(@RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        try {
            // 使用反射调用方法
            java.lang.reflect.Method method = sysProjectService.getClass().getMethod("getUserProjectCodes");
            return (List<String>) method.invoke(sysProjectService);
        } catch (Exception e) {
            throw new RuntimeException("调用系统项目服务失败", e);
        }
    }
}
