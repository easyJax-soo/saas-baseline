package com.baseline.frame.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@ConfigurationProperties(prefix = "cors")
public class GlobalCorsConfig extends CorsConfiguration {

    @Bean(name = "corsFilter")
    public FilterRegistrationBean corsFilter() {
        // 1. 使用绑定的 CORS 配置
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", this);

        // 2. 返回新的 CorsFilter
        CorsFilter corsFilter = new CorsFilter(corsConfigurationSource);
        FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>(corsFilter);
        filterRegistrationBean.setOrder(-101);  // 设置顺序在 Spring Security 过滤器之前
        return filterRegistrationBean;
    }
}
