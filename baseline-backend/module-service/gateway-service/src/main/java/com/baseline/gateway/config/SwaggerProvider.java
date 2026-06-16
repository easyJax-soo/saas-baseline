package com.baseline.gateway.config;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;


/**
 * 聚合系统接口
 *
 * @author ruoyi
 */
@Component
@Primary
@Profile("!prod") //不是生产环境才加载
public class SwaggerProvider implements SwaggerResourcesProvider, WebFluxConfigurer
{
    /**
     * Swagger2默认的url后缀
     */
    public static final String SWAGGER2URL = "/v2/api-docs";

    /**
     * 网关路由
     */
    @Lazy
    @Autowired
    private RouteLocator routeLocator;

    @Autowired
    private GatewayProperties gatewayProperties;

    /**
     * 聚合其他服务接口,,这个地方修改。。。。。
     *
     * @return
     */
    @Override
    public List<SwaggerResource> get()
    {
        //接口资源列表
        List<SwaggerResource> resources = new ArrayList<>();
        //服务名称列表
        List<String> routeHosts = new ArrayList<>();
        Map<String, String> serviceName = new HashMap<>();
        // 获取所有可用的应用名称
        routeLocator.getRoutes()
                .filter(route -> route.getUri().getHost() != null)
                .map(route -> {
                    String sName = String.valueOf(route.getMetadata().getOrDefault("serviceName", route.getUri().getHost()));
                    serviceName.put(route.getUri().getHost(), sName);
                    return route;
                })
                .filter(route -> Objects.equals(route.getUri().getScheme(), "lb"))
                .subscribe(route -> routeHosts.add(route.getUri().getHost()));


        //去重，多负载服务只添加一次
        Set<String> existsServer = new HashSet<>();
        routeHosts.forEach(instance -> {
            // 拼接url ，请求swagger的url
            String path = instance;
            if (instance.contains("-service")) {
                path = instance.substring(0, instance.indexOf("-service"));
            }
            String url ="/"+ path.toLowerCase() + SWAGGER2URL;
            if (!existsServer.contains(url)) {
                existsServer.add(url);
                SwaggerResource swaggerResource = new SwaggerResource();
                swaggerResource.setUrl(url);
                swaggerResource.setName(serviceName.get(instance));
                resources.add(swaggerResource);
            }
        });
        return resources;
    }
    private SwaggerResource swaggerResource(String name, String location){
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        /** swagger-ui 地址 */
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
    }
}
