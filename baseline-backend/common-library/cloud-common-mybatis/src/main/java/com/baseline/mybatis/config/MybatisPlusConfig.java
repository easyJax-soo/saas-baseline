package com.baseline.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baseline.mybatis.Interceptor.MyBlockAttackInnerInterceptor;
import com.baseline.mybatis.Interceptor.HierarchyTenantLineInnerInterceptor;
import com.baseline.mybatis.Interceptor.MyTenantLineInnerInterceptor;
import com.baseline.mybatis.Interceptor.PlusDataPermissionInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.baseline.**.mapper"})
@ComponentScan(basePackages = "com.baseline.mybatis")
public class MybatisPlusConfig {

    @Autowired(required = false)
    private MyTenantLineInnerInterceptor myTenantLineInnerInterceptor;

    @Autowired(required = false)
    private HierarchyTenantLineInnerInterceptor hierarchyTenantLineInnerInterceptor;

    @Autowired
    private TenantProperties tenantProperties;

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        //多租户插件
        if (tenantProperties.getEnable()) {
            // 优先使用层级租户拦截器
            if (hierarchyTenantLineInnerInterceptor != null) {
                interceptor.addInnerInterceptor(hierarchyTenantLineInnerInterceptor);
            } else if (myTenantLineInnerInterceptor != null) {
                interceptor.addInnerInterceptor(myTenantLineInnerInterceptor);
            }
        }

        //数据权限
        interceptor.addInnerInterceptor(dataPermissionInterceptor());

        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());

        //防止全表更新与删除插件: BlockAttackInnerInterceptor
        interceptor.addInnerInterceptor(new MyBlockAttackInnerInterceptor());

        return interceptor;
    }


    /**
     * 数据权限拦截器
     */
    public PlusDataPermissionInterceptor dataPermissionInterceptor() {
        return new PlusDataPermissionInterceptor();
    }


    /**
     * 乐观锁插件 当要更新一条记录的时候，希望这条记录没有被别人更新
     * https://mybatis.plus/guide/interceptor-optimistic-locker.html#optimisticlockerinnerinterceptor
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

}