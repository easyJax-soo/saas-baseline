package com.baseline.common.service;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.SysOperLogBizDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 系统日志业务服务接口
 *
 * @author system
 */
public interface ISysLogBizService {

    /**
     * 保存系统操作日志
     * @param sysOperLogBizDTO 操作日志DTO
     * @param source 请求来源
     * @return 是否成功
     */
    boolean saveOperLog(@RequestBody SysOperLogBizDTO sysOperLogBizDTO, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
