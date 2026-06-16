package com.baseline.common.service.factory;

import com.baseline.common.dto.SysOperLogBizDTO;
import com.baseline.common.service.ISysLogBizService;
import org.springframework.cloud.openfeign.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 日志服务 Feign 降级工厂
 *
 * @author system
 */
@Slf4j
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<ISysLogBizService> {

    @Override
    public ISysLogBizService create(Throwable throwable) {
        log.error("日志服务调用失败:{}", throwable.getMessage());
        return new ISysLogBizService() {
            @Override
            public boolean saveOperLog(SysOperLogBizDTO sysOperLogBizDTO, String source) {
                log.error("保存操作日志失败，返回false");
                return false;
            }
        };
    }
}
