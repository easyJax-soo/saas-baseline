package com.baseline.gateway.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import com.baseline.core.properties.IgnoreWhiteProperties;
import com.baseline.utils.security.SaTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [Sa-Token 权限认证] 全局配置类
 */
@Configuration
@Slf4j
public class SaTokenConfigure {

    // 排除过滤的 uri 地址，nacos自行添加
    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    /**
     * 注册 [Sa-Token全局过滤器]
     */
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 指定 [拦截路由]
                .addInclude("/**")    /* 拦截所有path */
                // 指定 [放行路由]
                .setExcludeList(ignoreWhite.getWhites())
                // 指定[认证函数]: 每次请求执行
                .setAuth(obj -> {
                    String requestPath = SaHolder.getRequest().getRequestPath();

                    SaRouter.match("/*/adminApi/**", r -> {
                        log.debug("admin token剩余活跃时间：{}", SaTokenUtils.ADMIN.getTokenActiveTimeout());
                        SaTokenUtils.ADMIN.checkLogin();
                    });

                    SaRouter.match("/*/webApi/**", r -> {
                        log.debug("member token剩余活跃时间：{}", SaTokenUtils.MEMBER.getTokenActiveTimeout());
                        SaTokenUtils.MEMBER.checkLogin();
                    });
                })
                ;
    }
}
