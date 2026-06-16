package com.baseline.common.service.impl;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.dto.SysUserDetailBizDTO;
import com.baseline.common.dto.SysUserFilterBizDTO;
import com.baseline.common.service.ISysUserBizService;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.common.vo.SysUserVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * 用户业务服务本地实现
 * 用于单体模式下的本地调用
 */
@Service
public class SysUserBizServiceImpl implements ISysUserBizService {

    @Resource(name = "sysUserServiceImpl")
    private Object sysUserService;

    @Override
    public LoginUserBizVO getLoginUserByUsername(LoginUserBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        try {
            java.lang.reflect.Method method = sysUserService.getClass().getMethod("getLoginUserByUsername", LoginUserBizDTO.class);
            return (LoginUserBizVO) method.invoke(sysUserService, dto);
        } catch (Exception e) {
            throw new RuntimeException("调用用户服务失败", e);
        }
    }

    @Override
    public SysUserVO detail(SysUserDetailBizDTO dto, String source) {
        try {
            java.lang.reflect.Method method = sysUserService.getClass().getMethod("detail", SysUserDetailBizDTO.class);
            return (SysUserVO) method.invoke(sysUserService, dto);
        } catch (Exception e) {
            throw new RuntimeException("调用用户详情服务失败", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SysUserVO> simpleList(SysUserFilterBizDTO dto, String source) {
        try {
            java.lang.reflect.Method method = sysUserService.getClass().getMethod("simpleList", SysUserFilterBizDTO.class);
            return (List<SysUserVO>) method.invoke(sysUserService, dto);
        } catch (Exception e) {
            throw new RuntimeException("调用用户列表服务失败", e);
        }
    }
}
