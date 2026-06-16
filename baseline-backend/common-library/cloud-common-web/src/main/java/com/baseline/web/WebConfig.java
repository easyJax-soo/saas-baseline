package com.baseline.web;

import com.baseline.jackson.JacksonUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@ComponentScan(basePackages = {"com.baseline"})
public class WebConfig implements WebMvcConfigurer {

    /**
     * 给每个微服务节点也注册一份 CORS Filter。
     * 网关已经在 spring.cloud.gateway.globalcors 处理过跨域，但当浏览器（或 knife4j 聚合文档）
     * 因为某些重定向 / 直连场景命中节点端口（如 38081）时，没有 CORS 头会导致预检失败。
     * Why: knife4j 调用 /auth/v2/api-docs 触发 302 到 http://<节点IP>:38081/login 时跨域失败。
     */
    @Bean
    @ConditionalOnMissingBean(name = "corsFilter")
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    /**
     * 解决不能返回字符串的问题
     * @param converters 数据转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        HttpMessageConverter<?> jackson2HttpMessageConverter= converters.stream().filter(v->v.getClass().equals(MappingJackson2HttpMessageConverter.class)).findFirst().orElse(null);
        converters.remove(jackson2HttpMessageConverter);
        converters.add(0,jackson2HttpMessageConverter);
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }


    // 
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(JacksonUtil.getObjectMapper());
        return converter;
    }
}
