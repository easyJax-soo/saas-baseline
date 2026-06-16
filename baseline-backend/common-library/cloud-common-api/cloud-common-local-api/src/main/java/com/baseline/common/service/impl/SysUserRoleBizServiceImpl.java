package com.baseline.common.service.impl;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.UserRoleBizDTO;
import com.baseline.common.service.ISysUserRoleBizService;
import com.baseline.common.vo.UserRoleBizVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 用户角色业务服务本地实现
 * 用于单体模式下的本地调用
 */
@Service
public class SysUserRoleBizServiceImpl implements ISysUserRoleBizService {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public List<UserRoleBizVO> getRolesByUserId(UserRoleBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        try {
            // 动态获取 ISysRoleService bean
            Object sysRoleService = applicationContext.getBean("sysRoleServiceImpl");
            
            // 使用反射调用方法
            java.lang.reflect.Method method = sysRoleService.getClass().getMethod("getRolesByUserId", UserRoleBizDTO.class);
            return (List<UserRoleBizVO>) method.invoke(sysRoleService, dto);
        } catch (Exception e) {
            throw new RuntimeException("调用用户角色服务失败", e);
        }
    }
}
