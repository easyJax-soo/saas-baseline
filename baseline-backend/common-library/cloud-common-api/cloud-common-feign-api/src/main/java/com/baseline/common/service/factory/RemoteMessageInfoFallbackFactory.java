package com.baseline.common.service.factory;

import com.baseline.common.service.ISysMessageInfoBizService;
import com.baseline.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteMessageInfoFallbackFactory implements FallbackFactory<ISysMessageInfoBizService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteMessageInfoFallbackFactory.class);

    @Override
    public ISysMessageInfoBizService create(Throwable cause)
    {
        log.error("消息中心接口调用失败:{}", cause.getMessage());
        return (dto, source) -> {
            throw new BusinessException(String.format("保存消息失败:%s", cause.getMessage()));
        };
    }
}
