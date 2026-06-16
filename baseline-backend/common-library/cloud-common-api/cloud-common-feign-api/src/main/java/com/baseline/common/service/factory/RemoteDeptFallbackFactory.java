package com.baseline.common.service.factory;

import com.baseline.common.dto.SysDeptFilterBizDTO;
import com.baseline.common.service.ISysDeptBizService;
import com.baseline.common.vo.SysDeptVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 部门服务降级处理
 * 
 * @author cascade
 * @date 2025/11/16
 */
@Component
public class RemoteDeptFallbackFactory implements FallbackFactory<ISysDeptBizService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteDeptFallbackFactory.class);

    @Override
    public ISysDeptBizService create(Throwable throwable)
    {
        log.error("部门服务调用失败:{}", throwable.getMessage());
        return new ISysDeptBizService()
        {
            @Override
            public List<SysDeptVO> getDeptVOList(SysDeptFilterBizDTO dto, String source) {
                log.error("部门列表获取失败:{}", throwable.getMessage());
                return Collections.emptyList();
            }
        };
    }
}
