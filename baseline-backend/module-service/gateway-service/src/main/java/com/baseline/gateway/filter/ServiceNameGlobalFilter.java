package com.baseline.gateway.filter;

import com.baseline.core.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 设置服务名
 */
@Slf4j
@Component
public class ServiceNameGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //不从配置获取主要是配置在nacos，外网无法访问
        String license = "2099-04-04";
        // 2. 解析配置时间（转换为UTC时区）
        LocalDate licenseDate = LocalDate.parse(license);
        ZonedDateTime expiration = licenseDate.atStartOfDay(ZoneId.of("UTC"));
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));

        // 3. 验证当前时间是否在有效期前
        if (!now.isBefore(expiration)) {
            // 4. 不在有效期内，许可证过期处理
            log.warn("许可证过期: {}", exchange.getRequest().getURI());
            return ServletUtils.webFluxResponseWriter(
                    exchange.getResponse(),
                    "系统错误，访问受限",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }



        // 1. 获取原始请求
        ServerHttpRequest originalRequest = exchange.getRequest();

        //获取路径的第一个path
        String fullPath = originalRequest.getPath().value();
        Path path = Paths.get(fullPath);

        //服务名
        String servicePrefix = null;
        //接口模块名称
        String apiPrefix = null;
        if (path.getNameCount() > 1) { // 确保有至少两个路径段
            servicePrefix = path.getName(0).toString();
            apiPrefix = path.getName(1).toString();
        }
        //auth服务API不区分模块
        if(servicePrefix.equals("auth")){
            apiPrefix = null;
        }

        // 2. 添加自定义请求头
        ServerHttpRequest mutatedRequest = originalRequest.mutate()
                .header("Hc-Service-Prefix", servicePrefix)
                .header("Hc-Api-Prefix", apiPrefix)
                .build();

        // 3. 使用突变后的请求构建新的交换对象，并继续过滤链
        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    @Override
    public int getOrder() {
        // 设置过滤器的执行顺序，确保在需要之前执行
        return -99; // 比如在你的XssFilter (-100) 之后执行
    }
}
