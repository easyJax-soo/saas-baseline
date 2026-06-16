package com.baseline.common.service;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.dto.SysUserDetailBizDTO;
import com.baseline.common.dto.SysUserFilterBizDTO;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.common.vo.SysUserVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
public interface ISysUserBizService {

    /**
     * 通过用户名获取管理后台的登录用户信息
     */
    LoginUserBizVO getLoginUserByUsername(LoginUserBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取用户详情
     *
     * @param dto 用户详情查询条件
     * @param source 来源标识
     * @return 用户信息
     */
    SysUserVO detail(SysUserDetailBizDTO dto, String source);

    /**
     * 获取用户简单列表
     *
     * @param dto 查询条件
     * @param source 来源标识
     * @return 用户列表
     */
    List<SysUserVO> simpleList(SysUserFilterBizDTO dto, String source);
}
