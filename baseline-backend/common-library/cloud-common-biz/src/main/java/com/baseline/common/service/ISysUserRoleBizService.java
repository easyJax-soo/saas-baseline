package com.baseline.common.service;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.UserRoleBizDTO;
import com.baseline.common.vo.UserRoleBizVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
public interface ISysUserRoleBizService {

    /**
     * 通过用户 ID获取 角色列表
     */
    List<UserRoleBizVO> getRolesByUserId(UserRoleBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
