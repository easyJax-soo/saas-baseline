package com.baseline.system.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 添加接口前缀
 */
@Slf4j
@Configuration
@Order(Ordered.LOWEST_PRECEDENCE) // 最低优先级，后执行添加子模块前缀
public class SystemApiPrefixConfig implements WebMvcConfigurer {

    @Value("${service-prefix.system:}")
    private String systemPrefix;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 构建前缀：如果 systemPrefix 为空，则只使用子模块前缀
        String adminApiPrefix = (systemPrefix == null || systemPrefix.trim().isEmpty()) ? "/adminApi" : systemPrefix + "/adminApi";
        String webApiPrefix = (systemPrefix == null || systemPrefix.trim().isEmpty()) ? "/webApi" : systemPrefix + "/webApi";
        String feignApiPrefix = (systemPrefix == null || systemPrefix.trim().isEmpty()) ? "/feignApi" : systemPrefix + "/feignApi";

        // 为 system 服务的不同模块添加前缀
        configurer
                .addPathPrefix(adminApiPrefix, c -> {
                    return c.getPackage().getName().startsWith("com.baseline.system.controller.admin");
                })
                .addPathPrefix(webApiPrefix, c -> {
                    return c.getPackage().getName().startsWith("com.baseline.system.controller.api");
                })
                .addPathPrefix(feignApiPrefix, c -> {
                    return c.getPackage().getName().startsWith("com.baseline.system.controller.feign");
                });
    }
}
