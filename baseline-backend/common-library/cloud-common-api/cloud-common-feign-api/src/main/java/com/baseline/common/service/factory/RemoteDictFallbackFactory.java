package com.baseline.common.service.factory;

import com.baseline.common.dto.DictBizDTO;
import com.baseline.common.service.ISysDictBizService;
import com.baseline.common.vo.DictBizVO;
import com.baseline.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteDictFallbackFactory implements FallbackFactory<ISysDictBizService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteDictFallbackFactory.class);

    @Override
    public ISysDictBizService create(Throwable throwable)
    {
        log.error("字典服务调用失败:{}", throwable.getMessage());
        return new ISysDictBizService()
        {
            @Override
            public List<DictBizVO> getDictDataListByCode(DictBizDTO dto, String source) {
                throw new BusinessException("获取字典失败:" + throwable.getMessage());
            }
        };
    }
}
