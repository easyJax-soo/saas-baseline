package com.baseline.common.service;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.DictBizDTO;
import com.baseline.common.vo.DictBizVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
public interface ISysDictBizService {

    /**
     * 根据字典类型获取字典列表
     */
    List<DictBizVO> getDictDataListByCode(DictBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
