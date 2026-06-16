package com.baseline.frame.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE) // 最高优先级，先执行添加 /system 前缀
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    PrefixProperties properties;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

        // 为不同包下的控制器类添加访问前缀
        configurer
                .addPathPrefix(properties.getAuth(), c -> {
                    return c.getPackage().getName().startsWith("com.baseline.auth");
                });
                // system 前缀的配置移到 SystemApiPrefixConfig 中处理，避免冲突
    }
}
