package com.baseline.web;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "web")
public class WebProperties {

    private List<String> customResponseUrl = new ArrayList<>();

}
