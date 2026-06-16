package com.baseline.common.service.impl;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.UserPermissionBizDTO;
import com.baseline.common.service.ISysUserPermissionBizService;
import com.baseline.common.vo.UserPermissionBizVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 用户权限业务服务本地实现
 * 用于单体模式下的本地调用
 */
@Service
public class SysUserPermissionBizServiceImpl implements ISysUserPermissionBizService {

    @Resource(name = "sysPermissionServiceImpl")
    private Object sysPermissionService;

    @Override
    public List<UserPermissionBizVO> getPermissionsByUserIdAndRoleId(UserPermissionBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        try {
            // 使用反射调用方法
            java.lang.reflect.Method method = sysPermissionService.getClass().getMethod("getPermissionsByUserIdAndRoleId", UserPermissionBizDTO.class);
            return (List<UserPermissionBizVO>) method.invoke(sysPermissionService, dto);
        } catch (Exception e) {
            throw new RuntimeException("调用用户权限服务失败", e);
        }
    }
}
