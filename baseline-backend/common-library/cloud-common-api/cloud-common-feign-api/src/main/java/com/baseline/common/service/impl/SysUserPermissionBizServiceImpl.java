package com.baseline.common.service.impl;

import com.baseline.common.dto.UserPermissionBizDTO;
import com.baseline.common.service.ISysUserPermissionBizService;
import com.baseline.common.service.factory.RemoteUserPermissionFallbackFactory;
import com.baseline.common.vo.UserPermissionBizVO;
import com.baseline.common.constant.SecurityConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(contextId = "sysUserPermissionBizService", value = "system-service", fallbackFactory = RemoteUserPermissionFallbackFactory.class)
public interface SysUserPermissionBizServiceImpl extends ISysUserPermissionBizService {
    @Override
    @PostMapping("/feignApi/permission/userRolePermission")
    List<UserPermissionBizVO> getPermissionsByUserIdAndRoleId(UserPermissionBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
