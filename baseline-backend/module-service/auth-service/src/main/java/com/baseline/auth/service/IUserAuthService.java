package com.baseline.auth.service;

import com.baseline.auth.dto.LoginBashDTO;
import com.baseline.auth.vo.LoginTokenVO;

public interface IUserAuthService {

    LoginTokenVO doLogin(LoginBashDTO<?> dto);

    boolean doLogout();
}
