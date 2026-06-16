package com.baseline.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baseline.mybatis.Interceptor.HierarchyTenantLineInnerInterceptor;
import com.baseline.mybatis.handler.DefaultHierarchyTenantHandler;
import com.baseline.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 层级租户配置
 * 支持父租户查看子租户数据
 *
 * @author csy
 * @since 2025-11-17
 */
@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@AutoConfigureBefore(MybatisPlusConfig.class)
public class HierarchyTenantConfig {

    private final TenantProperties tenantProperties;
    private final DefaultHierarchyTenantHandler hierarchyTenantHandler;

    /**
     * 层级租户插件配置
     * 当启用层级租户时，父租户可以查看子租户的数据
     *
     * @return HierarchyTenantLineInnerInterceptor
     */
    @Bean
    @ConditionalOnProperty(name = "tenant.hierarchy.enable", havingValue = "true", matchIfMissing = false)
    public HierarchyTenantLineInnerInterceptor hierarchyTenantLineInnerInterceptor() {
        return new HierarchyTenantLineInnerInterceptor(new TenantLineHandler() {
            /**
             * 获取租户ID
             * @return Expression
             */
            @Override
            public Expression getTenantId() {
                String tenantId = String.valueOf(SecurityUtils.getTenantId());
                if (tenantId != null) {
                    return new StringValue(tenantId);
                }
                return new NullValue();
            }

            /**
             * 获取多租户的字段名
             * @return String
             */
            @Override
            public String getTenantIdColumn() {
                return tenantProperties.getColumn();
            }

            /**
             * 过滤不需要根据租户隔离的表
             * 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
             * @param tableName 表名
             */
            @Override
            public boolean ignoreTable(String tableName) {
                return tenantProperties.getExclusionTable().stream().anyMatch(
                        (t) -> t.equalsIgnoreCase(tableName)
                );
            }
        }, hierarchyTenantHandler);
    }
}
