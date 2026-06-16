package com.baseline.frame.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * [Sa-Token 权限认证] 全局配置类
 *
 * 注意: SaServletFilter 基于 javax.servlet，与 Spring Boot 3.x (jakarta.servlet) 不兼容
 * 此配置暂时禁用，需要使用 Spring Security 集成方式重新实现
 */
@Configuration
@Slf4j
public class SaTokenConfigure {

    // 排除过滤的 uri 地址，nacos自行添加
    // @Autowired
    // private IgnoreWhiteProperties ignoreWhite;

    /**
     * 注册 [Sa-Token全局过滤器]
     *
     * TODO: 需要使用 Spring Security 集成方式替代 SaServletFilter
     * Sa-Token 与 Spring Boot 3.x (Jakarta EE) 的集成方式不同
     */
    // @Bean
    // @SuppressWarnings({"rawtypes", "unchecked"})
    // public FilterRegistrationBean getSaServletFilter() {
    //     FilterRegistrationBean registration = new FilterRegistrationBean();
    //     registration.setFilter(new SaServletFilter()
    //         // 指定 [拦截路由]
    //         .addInclude("/**")    /* 拦截所有path */
    //         // 指定 [放行路由]
    //         .setExcludeList(ignoreWhite.getWhites())
    //         // 指定[认证函数]: 每次请求执行
    //         .setAuth(obj -> {
    //             String requestPath = SaHolder.getRequest().getRequestPath();
    //             log.info("requestPath：{}", requestPath);
    //
    //             SaRouter.match("/*/adminApi/**", r -> {
    //                 log.debug("admin token剩余活跃时间：{}", SaTokenUtils.ADMIN.getTokenActiveTimeout());
    //                 SaTokenUtils.ADMIN.checkLogin();
    //             });
    //
    //             SaRouter.match("/*/webApi/**", r -> {
    //                 log.debug("member token剩余活跃时间：{}", SaTokenUtils.MEMBER.getTokenActiveTimeout());
    //                 SaTokenUtils.MEMBER.checkLogin();
    //             });
    //         })
    //     );
    //
    //     // 设置过滤器优先级，确保在其他过滤器之前执行
    //     registration.setOrder(-100);
    //
    //     return registration;
    // }
}
