package com.baseline.oss.config;

import com.baseline.oss.OssProperties;
import com.baseline.oss.OssTemplateFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssAutoConfiguration {

    @Bean
    public OssTemplateFactory ossTemplateFactory() {
        return new OssTemplateFactory();
    }
}