package com.baseline.common.service.factory;

import com.baseline.common.service.ISysProjectBizService;
import org.springframework.cloud.openfeign.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目权限服务降级处理
 *
 * @author system
 */
@Slf4j
@Component
public class RemoteProjectFallbackFactory implements FallbackFactory<ISysProjectBizService>
{
    @Override
    public ISysProjectBizService create(Throwable throwable)
    {
        log.error("项目权限服务调用失败:{}", throwable.getMessage());
        return new ISysProjectBizService()
        {
            @Override
            public List<String> getUserProjectCodes(String source)
            {
                log.error("获取用户项目权限列表失败，返回空列表");
                return new ArrayList<>();
            }
        };
    }
}
