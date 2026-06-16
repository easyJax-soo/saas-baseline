package com.baseline.auth.controller;

import com.baseline.auth.service.impl.CaptchaCodeServiceImpl;
import com.baseline.auth.vo.CaptchaCodeVO;
import com.baseline.auth.vo.CaptchaEnabledVO;
import com.baseline.log.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "验证码")
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaCodeServiceImpl captchaCodeServiceImpl;

    @ApiOperation("验证码图片")
    @GetMapping("/image")
    @Log(title = "验证码图片")
    public CaptchaCodeVO getCaptcha(){
        return captchaCodeServiceImpl.createCaptcha();
    }

    @ApiOperation("验证码是否开启")
    @GetMapping("/isEnabled")
    @Log(title = "验证码是否开启")
    public CaptchaEnabledVO isEnabled(){
        return captchaCodeServiceImpl.isEnabled();
    }
}