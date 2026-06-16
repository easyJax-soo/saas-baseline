package com.baseline.auth.controller;

import com.baseline.auth.dto.LoginBashDTO;
import com.baseline.auth.service.IUserAuthService;
import com.baseline.auth.vo.LoginTokenVO;
import com.baseline.log.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户认证")
@RestController
@RequestMapping("/user")
public class UserAuthController {
    @Autowired
    private IUserAuthService userAuthService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    @Log(title = "用户登录")
    public LoginTokenVO ssoAuth(@Validated @RequestBody LoginBashDTO<?> dto) {
       return userAuthService.doLogin(dto);
    }


    @ApiOperation("用户登出")
    @PostMapping("/logout")
    @Log(title = "用户登出")
    public boolean logout() {
        return userAuthService.doLogout();
    }

}
