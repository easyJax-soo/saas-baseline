package com.baseline.auth.provider.common;

import com.baseline.auth.dto.LoginBashDTO;
import com.baseline.auth.vo.LoginTokenVO;
import com.baseline.core.exception.BusinessException;

public interface AuthenticationHandler {

    /**
     * 认证类型
     * @return
     */
    String getAuthType();

    /**
     * 认证请求
     * @param request 对应的具体登录请求DTO（如AccountPasswordLoginDTO）
     * @return 用户ID
     * @return
     * @throws BusinessException
     */
    LoginTokenVO authenticate(LoginBashDTO<?> request) throws BusinessException;

}