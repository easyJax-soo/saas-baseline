package com.baseline.common.service;

import com.baseline.common.constant.SecurityConstants;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 系统项目权限业务服务接口
 *
 * @author system
 */
@Service
public interface ISysProjectBizService {

    /**
     * 获取当前用户有权限访问的项目编码列表
     * @param source 请求来源
     * @return 项目编码列表
     */
    List<String> getUserProjectCodes(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
