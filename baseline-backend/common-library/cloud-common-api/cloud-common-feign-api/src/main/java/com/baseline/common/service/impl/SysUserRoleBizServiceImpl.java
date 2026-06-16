package com.baseline.common.service.impl;

import com.baseline.common.dto.UserRoleBizDTO;
import com.baseline.common.service.ISysUserRoleBizService;
import com.baseline.common.service.factory.RemoteUserRoleFallbackFactory;
import com.baseline.common.vo.UserRoleBizVO;
import com.baseline.common.constant.SecurityConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(contextId = "sysUserRoleBizService", value = "system-service", fallbackFactory = RemoteUserRoleFallbackFactory.class)
public interface SysUserRoleBizServiceImpl extends ISysUserRoleBizService {
    @Override
    @PostMapping("/feignApi/role/userRole")
    List<UserRoleBizVO> getRolesByUserId(UserRoleBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
