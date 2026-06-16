package com.baseline.auth.config;

import cn.dev33.satoken.oauth2.data.loader.SaOAuth2DataLoader;
import cn.dev33.satoken.oauth2.data.model.loader.SaClientModel;
import cn.hutool.core.util.ObjectUtil;
import com.baseline.auth.service.IOauthClientDetailsService;
import com.baseline.auth.vo.OauthClientDetailsDetailVO;
import com.baseline.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Sa-Token OAuth2：自定义数据加载器
 */
@Component
public class SaOAuth2DataLoaderImpl implements SaOAuth2DataLoader {

    @Autowired
    IOauthClientDetailsService oauthClientDetailsService;

    // 根据 clientId 获取 Client 信息
    @Override
    public SaClientModel getClientModel(String clientId) {

        OauthClientDetailsDetailVO client = oauthClientDetailsService.getDetailByClientId(clientId);
        if(ObjectUtil.isNull(client)){
            throw new BusinessException("无效ClientId：" + clientId);
        }

        return new SaClientModel()
                .setClientId(client.getClientId())    // client id
                .setClientSecret(client.getClientSecret())    // client 秘钥
                .addAllowRedirectUris(client.getWebServerRedirectUri())    // 所有允许授权的 url
                .addContractScopes(client.getScope())    // 所有签约的权限
                .addAllowGrantTypes(
                        client.getAuthorizedGrantTypes()
                );
    }

    // 根据 clientId 和 loginId 获取 openid
    @Override
    public String getOpenid(String clientId, Object loginId) {
        // 此处使用框架默认算法生成 openid，真实环境建议改为从数据库查询
        return SaOAuth2DataLoader.super.getOpenid(clientId, loginId);
    }
}
