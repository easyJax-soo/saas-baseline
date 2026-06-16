package com.baseline.common.service;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.UserPermissionBizDTO;
import com.baseline.common.vo.UserPermissionBizVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
public interface ISysUserPermissionBizService {

    /**
     * 通过用户 ID和角色ID获取权限列表
     * 如果roleId为null，则获取用户所有权限（支持租户管理员）
     */
    List<UserPermissionBizVO> getPermissionsByUserIdAndRoleId(UserPermissionBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
