package com.baseline.log.service;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.SysOperLogBizDTO;
import com.baseline.common.service.ISysLogBizService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 异步调用日志服务
 * 
 * @author ruoyi
 */
@Service
public class AsyncLogService
{
    @Resource
    private ISysLogBizService sysLogBizService;

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveSysLog(SysOperLogBizDTO sysOperLogBizDTO) throws Exception
    {
        sysLogBizService.saveOperLog(sysOperLogBizDTO, SecurityConstants.INNER);
    }
}
