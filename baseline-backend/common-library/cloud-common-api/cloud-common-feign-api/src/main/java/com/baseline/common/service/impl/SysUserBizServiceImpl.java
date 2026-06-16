package com.baseline.common.service.impl;

import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.dto.SysUserDetailBizDTO;
import com.baseline.common.dto.SysUserFilterBizDTO;
import com.baseline.common.service.factory.RemoteUserFallbackFactory;
import com.baseline.common.service.ISysUserBizService;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.common.vo.SysUserVO;
import com.baseline.common.constant.SecurityConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(contextId = "sysUserBizService", value = "system-service", fallbackFactory = RemoteUserFallbackFactory.class)
public interface SysUserBizServiceImpl extends ISysUserBizService {
    
    @Override
    @PostMapping("/feignApi/user/getUserByUsername")
    LoginUserBizVO getLoginUserByUsername(LoginUserBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @PostMapping("/feignApi/user/detail")
    SysUserVO detail(@RequestBody SysUserDetailBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @PostMapping("/feignApi/user/simpleList")
    List<SysUserVO> simpleList(@RequestBody SysUserFilterBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
