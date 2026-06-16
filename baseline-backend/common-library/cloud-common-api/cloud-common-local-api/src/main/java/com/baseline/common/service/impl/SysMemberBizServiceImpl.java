package com.baseline.common.service.impl;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.dto.SysMemberDetailBizDTO;
import com.baseline.common.dto.SysMemberFilterBizDTO;
import com.baseline.common.service.ISysMemberBizService;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.common.vo.SysMemberVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 会员业务服务本地实现
 * 用于单体模式下的本地调用
 */
@Service
public class SysMemberBizServiceImpl implements ISysMemberBizService {

    @Resource(name = "sysMemberServiceImpl")
    private Object sysMemberService;

    @Override
    public LoginUserBizVO getLoginUserByUsername(LoginUserBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        try {
            java.lang.reflect.Method method = sysMemberService.getClass().getMethod("getLoginUserByUsername", LoginUserBizDTO.class);
            return (LoginUserBizVO) method.invoke(sysMemberService, dto);
        } catch (Exception e) {
            throw new RuntimeException("调用会员服务失败", e);
        }
    }

    @Override
    public SysMemberVO detail(SysMemberDetailBizDTO dto, String source) {
        try {
            java.lang.reflect.Method method = sysMemberService.getClass().getMethod("detail", SysMemberDetailBizDTO.class);
            return (SysMemberVO) method.invoke(sysMemberService, dto);
        } catch (Exception e) {
            throw new RuntimeException("调用会员详情服务失败", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SysMemberVO> simpleList(SysMemberFilterBizDTO dto, String source) {
        try {
            java.lang.reflect.Method method = sysMemberService.getClass().getMethod("simpleList", SysMemberFilterBizDTO.class);
            return (List<SysMemberVO>) method.invoke(sysMemberService, dto);
        } catch (Exception e) {
            throw new RuntimeException("调用会员列表服务失败", e);
        }
    }
}
