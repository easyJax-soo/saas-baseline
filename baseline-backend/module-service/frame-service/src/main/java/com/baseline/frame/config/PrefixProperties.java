package com.baseline.frame.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "service-prefix")
@Data
public class PrefixProperties {

    private String auth;

    private String system;

}
