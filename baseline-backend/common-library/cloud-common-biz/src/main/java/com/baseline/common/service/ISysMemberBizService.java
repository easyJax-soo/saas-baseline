package com.baseline.common.service;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.dto.SysMemberDetailBizDTO;
import com.baseline.common.dto.SysMemberFilterBizDTO;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.common.vo.SysMemberVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
public interface ISysMemberBizService {

    /**
     * 通过用户名获取会员登录用户信息
     */
    LoginUserBizVO getLoginUserByUsername(LoginUserBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取会员详情
     *
     * @param dto 会员详情查询条件
     * @param source 来源标识
     * @return 会员信息
     */
    SysMemberVO detail(SysMemberDetailBizDTO dto, String source);

    /**
     * 获取会员简单列表
     *
     * @param dto 查询条件
     * @param source 来源标识
     * @return 会员列表
     */
    List<SysMemberVO> simpleList(SysMemberFilterBizDTO dto, String source);
}
