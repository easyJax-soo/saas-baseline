package com.baseline.common.service.impl;

import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.dto.SysMemberDetailBizDTO;
import com.baseline.common.dto.SysMemberFilterBizDTO;
import com.baseline.common.service.ISysMemberBizService;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.common.vo.SysMemberVO;
import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.service.factory.RemoteMemberFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(contextId = "remoteMemberService", value = "system-service", fallbackFactory = RemoteMemberFallbackFactory.class)
public interface SysMemberBizServiceImpl extends ISysMemberBizService {

    @Override
    @PostMapping("/feignApi/member/getLoginUserByUsername")
    LoginUserBizVO getLoginUserByUsername(@RequestBody LoginUserBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @PostMapping("/feignApi/member/detail")
    SysMemberVO detail(@RequestBody SysMemberDetailBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @PostMapping("/feignApi/member/simpleList")
    List<SysMemberVO> simpleList(@RequestBody SysMemberFilterBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
