package com.baseline.common.service.impl;

import com.baseline.common.dto.DictBizDTO;
import com.baseline.common.service.ISysDictBizService;
import com.baseline.common.service.factory.RemoteDictFallbackFactory;
import com.baseline.common.vo.DictBizVO;
import com.baseline.common.constant.SecurityConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(contextId = "sysDictDataBizService", value = "system-service", fallbackFactory = RemoteDictFallbackFactory.class)
public interface SysDictBizServiceImpl extends ISysDictBizService {
    @Override
    @PostMapping("/feignApi/dict/dictData")
    List<DictBizVO> getDictDataListByCode(DictBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
