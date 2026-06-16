package com.baseline.auth.controller.admin;


import com.baseline.auth.service.ISsoServerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "SSO单点登录")
@RestController
//@RequestMapping
public class SsoServerController {

    @Autowired
    ISsoServerService ssoServerService;

//    // SSO-Server：统一认证地址
//    @RequestMapping("/auth")
//    public Object ssoAuth() {
//        return SaSsoServerProcessor.instance.ssoAuth();
//    }
//
//    // SSO-Server：RestAPI 登录接口
//    @PostMapping("/sso/doLogin")
//    public Object ssoDoLogin(@RequestBody AccountPasswordLoginDTO dto) {
//        return ssoServerService.doLogin(dto);
//    }
//
//    // SSO-Server：接收推送消息地址
//    @RequestMapping("/pushS")
//    public Object ssoPushS() {
//        return SaSsoServerProcessor.instance.ssoPushS();
//    }
//
//    // SSO-Server：单点注销
//    @RequestMapping("/signout")
//    public Object ssoSignout() {
//        return SaSsoServerProcessor.instance.ssoSignout();
//    }

}
