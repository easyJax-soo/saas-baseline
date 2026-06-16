package com.baseline.common.security.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import com.baseline.common.security.handler.ServiceCheckInterceptorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityTokenConfigure implements WebMvcConfigurer {
    @Autowired
    private ServiceCheckInterceptorHandler serviceCheckInterceptorHandler;

    /**
     * knife4j / swagger 自身文档与静态资源；本服务直接被命中时（绕过网关或被网关 302 后）也要放行，
     * 避免 Sa-Token 拦截链触发未登录 302 跳 /login。
     */
    private static final String[] SWAGGER_WHITELIST = new String[]{
            "/v2/api-docs",
            "/v2/api-docs/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/doc.html",
            "/favicon.ico"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 打开注解式鉴权功能 - 先执行，初始化 SaToken 上下文
        registry
                .addInterceptor(new SaInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(SWAGGER_WHITELIST);

        // 校验用户是否被封禁 - 后执行，此时 SaToken 上下文已就绪
        registry
                .addInterceptor(serviceCheckInterceptorHandler)
                .addPathPatterns("/**")
                .excludePathPatterns(SWAGGER_WHITELIST);

    }
}
