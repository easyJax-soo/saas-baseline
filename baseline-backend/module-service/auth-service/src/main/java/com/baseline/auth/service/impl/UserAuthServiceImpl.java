package com.baseline.auth.service.impl;

import com.baseline.auth.dto.LoginBashDTO;
import com.baseline.auth.provider.common.AuthHandlerRegistry;
import com.baseline.auth.provider.common.AuthenticationHandler;
import com.baseline.auth.service.IUserAuthService;
import com.baseline.auth.vo.LoginTokenVO;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.core.exception.BusinessException;
import com.baseline.utils.security.SaTokenUtils;
import com.baseline.utils.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl implements IUserAuthService {

    @Autowired
    private AuthHandlerRegistry authHandlerRegistry;
    @Override
    public LoginTokenVO doLogin(LoginBashDTO<?> dto) {
        String authType = dto.getAuthType();
        // 根据认证类型获取对应的处理器
        AuthenticationHandler handler = authHandlerRegistry.getHandler(authType);
        if (handler == null) {
            throw new BusinessException("不支持的认证方式");
        }
        return handler.authenticate(dto);
    }

    @Override
    public boolean doLogout() {
        LoginUserBizVO login = SecurityUtils.getLoginUser();

        if(login.getLoginType().equals(SaTokenUtils.LOGIN_ADMIN)){
            SaTokenUtils.ADMIN.logout();
        }else if(login.getLoginType().equals(SaTokenUtils.LOGIN_MEMBER)){
            SaTokenUtils.MEMBER.logout();
        }

        return true;
    }

}
