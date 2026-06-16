package com.baseline.mybatis.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 租户配置属性
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "tenant")
public class TenantProperties {

    /**
     * 是否开启租户模式
     */
    private Boolean enable = false; // 默认值设置为false

    /**
     * 多租户字段名称
     */
    private String column;

    /**
     * 需要排除的多租户的表
     */
    private List<String> exclusionTable;

    /**
     * 层级租户配置
     */
    private Hierarchy hierarchy = new Hierarchy();

    @Data
    public static class Hierarchy {
        /**
         * 是否开启层级租户模式
         */
        private Boolean enable = false; // 默认值设置为false
    }
}
