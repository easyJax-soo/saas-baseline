package com.baseline.auth.controller.admin;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "oauth2登录")
@RestController
//@RequestMapping("/oauth2")
public class Oauth2ServerController {
//
//
//    // 模式一：Code授权码 || 模式二：隐藏式
//    @RequestMapping("/authorize")
//    public Object authorize() {
//        return SaOAuth2ServerProcessor.instance.authorize();
//    }
//
//    // 用户登录
//    @RequestMapping("/doLogin")
//    public Object doLogin() {
//        return SaOAuth2ServerProcessor.instance.doLogin();
//    }
//
//    // 用户确认授权
//    @RequestMapping("/doConfirm")
//    public Object doConfirm() {
//        return SaOAuth2ServerProcessor.instance.doConfirm();
//    }
//
//    // Code 换 Access-Token || 模式三：密码式
//    @RequestMapping("/token")
//    public Object token() {
//        return SaOAuth2ServerProcessor.instance.token();
//    }
//
//    // Refresh-Token 刷新 Access-Token
//    @RequestMapping("/refresh")
//    public Object refresh() {
//        return SaOAuth2ServerProcessor.instance.refresh();
//    }
//
//    // 回收 Access-Token
//    @RequestMapping("/revoke")
//    public Object revoke() {
//        return SaOAuth2ServerProcessor.instance.revoke();
//    }
//
//    // 模式四：凭证式
//    @RequestMapping("/client_token")
//    public Object clientToken() {
//        return SaOAuth2ServerProcessor.instance.clientToken();
//    }

//    @GetMapping("/getRedirectUri")
//    public OauthRedirectUriVO getRedirectUri() {
//        boolean aa = StpKit.ADMIN.isLogin(10001);
//
//        System.out.println(aa);
//
//        // 获取变量
//        SaRequest req = SaHolder.getRequest();
//        SaOAuth2ServerConfig cfg = SaOAuth2Manager.getServerConfig();
//        SaOAuth2DataGenerate dataGenerate = SaOAuth2Manager.getDataGenerate();
//        SaOAuth2Template oauth2Template = SaOAuth2Manager.getTemplate();
//        String responseType = req.getParamNotNull(SaOAuth2Consts.Param.response_type);
//
//        // 1、先判断是否开启了指定的授权模式
//        SaOAuth2ServerProcessor.instance.checkAuthorizeResponseType(responseType, req, cfg);
//
//        // 2、如果尚未登录, 则先去登录
//        long loginId = SaOAuth2Manager.getStpLogic().getLoginId(0L);
//        if(loginId == 0L) {
//            throw new UnauthorizedException("未登录");
//        }
//
//        // 3、构建请求 Model
//        RequestAuthModel ra = SaOAuth2Manager.getDataResolver().readRequestAuthModel(req, loginId);
//
//        // 4、开发者自定义的授权前置检查
//        SaOAuth2Strategy.instance.userAuthorizeClientCheck.run(ra.loginId, ra.clientId);
//
//        // 5、校验：重定向域名是否合法
//        oauth2Template.checkRedirectUri(ra.clientId, ra.redirectUri);
//
//        // 6、校验：此次申请的Scope，该Client是否已经签约
//        oauth2Template.checkContractScope(ra.clientId, ra.scopes);
//
//        // 7、判断：如果此次申请的Scope，该用户尚未授权，则转到授权页面
//        boolean isNeedCarefulConfirm = oauth2Template.isNeedCarefulConfirm(ra.loginId, ra.clientId, ra.scopes);
//        if(isNeedCarefulConfirm) {
//            SaClientModel cm = oauth2Template.checkClientModel(ra.clientId);
//            if( ! cm.getIsAutoConfirm()) {
//                // code=411，需要用户手动确认授权
//                throw new UnauthorizedException("用户未手动授权");
//            }
//        }
//
//        // 8、判断授权类型，重定向到不同地址
//        //         如果是 授权码式，则：开始重定向授权，下放code
//        if(SaOAuth2Consts.ResponseType.code.equals(ra.responseType)) {
//            CodeModel codeModel = dataGenerate.generateCode(ra);
//            String redirectUri = dataGenerate.buildRedirectUri(ra.redirectUri, codeModel.code, ra.state);
//            OauthRedirectUriVO vo = new OauthRedirectUriVO();
//            vo.setRedirectUri(redirectUri);
//            return vo;
//        }
//
//        //         如果是 隐藏式，则：开始重定向授权，下放 token
//        if(SaOAuth2Consts.ResponseType.token.equals(ra.responseType)) {
//            AccessTokenModel at = dataGenerate.generateAccessToken(ra, false, null);
//            String redirectUri = dataGenerate.buildImplicitRedirectUri(ra.redirectUri, at.accessToken, ra.state);
//            OauthRedirectUriVO vo = new OauthRedirectUriVO();
//            vo.setRedirectUri(redirectUri);
//            return vo;
//        }
//
//        throw new BusinessException("无效 response_type: " + ra.responseType);
//    }
}
