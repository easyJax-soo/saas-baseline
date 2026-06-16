package com.baseline.frame.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 设置服务名过滤器
 * 模拟 Gateway 的 ServiceNameGlobalFilter 功能
 * 为单体应用的请求添加服务名称请求头
 */
@Component
@Slf4j
public class ServiceNameFilter implements Filter, Ordered {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 获取请求路径
        String fullPath = httpRequest.getRequestURI();

        Path path = Paths.get(fullPath);

        // 服务名
        final String servicePrefix;
        // 接口模块名称
        final String apiPrefix;

        if (path.getNameCount() > 1) { // 确保有至少两个路径段
            String tempServicePrefix = path.getName(0).toString();
            String tempApiPrefix = path.getName(1).toString();

            // auth服务API不区分模块
            if ("auth".equals(tempServicePrefix)) {
                tempApiPrefix = null;
            }

            servicePrefix = tempServicePrefix;
            apiPrefix = tempApiPrefix;

        } else {
            servicePrefix = null;
            apiPrefix = null;
        }

        // 创建包装的请求对象，添加自定义请求头
        HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
            private final Map<String, String> customHeaders = new HashMap<>();

            {
                if (servicePrefix != null) {
                    customHeaders.put("Hc-Service-Prefix", servicePrefix);
                }
                if (apiPrefix != null) {
                    customHeaders.put("Hc-Api-Prefix", apiPrefix);
                }
            }

            @Override
            public String getHeader(String name) {
                String customValue = customHeaders.get(name);
                return customValue != null ? customValue : super.getHeader(name);
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                Set<String> headerNames = new HashSet<>();
                Enumeration<String> originalHeaders = super.getHeaderNames();
                while (originalHeaders.hasMoreElements()) {
                    headerNames.add(originalHeaders.nextElement());
                }
                headerNames.addAll(customHeaders.keySet());
                return Collections.enumeration(headerNames);
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                String customValue = customHeaders.get(name);
                if (customValue != null) {
                    return Collections.enumeration(Arrays.asList(customValue));
                }
                return super.getHeaders(name);
            }
        };

        // 继续过滤链
        chain.doFilter(wrappedRequest, response);
    }

    @Override
    public int getOrder() {
        // 设置过滤器的执行顺序，确保在 ServiceCheckInterceptorHandler 之前执行
        return -99;
    }
}
