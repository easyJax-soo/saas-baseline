package com.baseline.common.feign;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.servlet.http.HttpServletRequest;

import com.baseline.common.constant.CommonConstants;
import com.baseline.common.constant.SecurityConstants;
import com.baseline.core.utils.IpUtils;
import com.baseline.core.utils.ServletUtils;
import com.baseline.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * feign 请求拦截器
 * 
 * @author ruoyi
 */
@Component
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor
{
    @Override
    public void apply(RequestTemplate requestTemplate)
    {

        String method = requestTemplate.method();
        String path = requestTemplate.url();
        String baseUrl = requestTemplate.feignTarget().url();
        String fullUrl = baseUrl + path;
        String serviceName = extractServiceName(baseUrl);

        log.debug("[Feign日志] 调用方法: {}, 目标URL: {}", method, fullUrl);

        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        if (StringUtils.isNotNull(httpServletRequest))
        {
            Map<String, String> headers = ServletUtils.getHeaders(httpServletRequest);
//            // 传递用户信息请求头，防止丢失
//            String userId = headers.get(SecurityConstants.DETAILS_USER_ID);
//            if (StringUtils.isNotEmpty(userId))
//            {
//                requestTemplate.header(SecurityConstants.DETAILS_USER_ID, userId);
//            }
////            String userKey = headers.get(SecurityConstants.USER_KEY);
////            if (StringUtils.isNotEmpty(userKey))
////            {
////                requestTemplate.header(SecurityConstants.USER_KEY, userKey);
////            }
//            String userName = headers.get(SecurityConstants.DETAILS_USERNAME);
//            if (StringUtils.isNotEmpty(userName))
//            {
//                requestTemplate.header(SecurityConstants.DETAILS_USERNAME, userName);
//            }
            String authentication = headers.get(SecurityConstants.AUTHORIZATION_HEADER);
            if (StringUtils.isNotEmpty(authentication))
            {
                requestTemplate.header(SecurityConstants.AUTHORIZATION_HEADER, authentication);
            }

            //设置请求头
            requestTemplate.header(SecurityConstants.FROM_SOURCE, SecurityConstants.INNER);
            requestTemplate.header(CommonConstants.HEADER_SERVICE_PREFIX, serviceName);
            requestTemplate.header(CommonConstants.HEADER_API_PREFIX, CommonConstants.FEIGN_API_PREFIX);

            // 配置客户端IP
            requestTemplate.header("X-Forwarded-For", IpUtils.getIpAddr());
        }


        // 获取租户信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null)
        {
            HttpServletRequest request = attributes.getRequest();
            String tenantId = request.getHeader(SecurityConstants.TENANT);

            // 设置自定义租户ID header
            if (StringUtils.isNotNull(tenantId))
            {
                requestTemplate.header(SecurityConstants.TENANT, tenantId);
            }
        }
    }

    /**
     * 提取服务名称
     * @param url
     * @return
     */
    public static String extractServiceName(String url) {
        // 正则表达式解释：
        // http://    匹配字面量
        // ([^/.-]+)  捕获组1：匹配一个或多个非"/", 非".", 非"-"的字符（这就是我们要的服务名主体部分）
        // [^/.]*     匹配0个或多个非"/", 非"."的字符（用于匹配可能存在的分隔符及后续内容，如"-service", "_api"等）
        // (?=[/.]|$) 正向先行断言：确保后面跟着的是"/", "."或者字符串结束（确保我们匹配的是主机名部分）
        Pattern pattern = Pattern.compile("http://([^/.-]+)[^/.]*(?=[/.]|$)");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            // 返回第一个捕获组，即完整的服务名
            return matcher.group(1);
        }
        return null; // 或在找不到时抛出异常/返回默认值
    }
}