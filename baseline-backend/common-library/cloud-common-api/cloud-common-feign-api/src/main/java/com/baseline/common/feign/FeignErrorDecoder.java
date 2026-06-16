package com.baseline.common.feign;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baseline.core.exception.BusinessException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        String body = extractBody(response); // 从响应体提取内容

        try {
            // 尝试解析为 JSON
            Map<String, String> errorMap = JSON.parseObject(body, Map.class);

            // 构建错误信息，添加"服务调用失败："前缀
            StringBuilder sb = new StringBuilder("服务调用失败：");
            sb.append(errorMap.getOrDefault("message", "feign调用错误"));

            // 只有error有值时才添加冒号和内容
            String error = errorMap.getOrDefault("error", "");
            if (!error.isEmpty()) {
                sb.append(": ").append(error);
            }
            // 只有path有值时才添加空格和内容
            String path = errorMap.getOrDefault("path", "");
            if (!path.isEmpty()) {
                sb.append(" [").append(path).append("]");
            }
            
            // 直接返回 BusinessException，不要在 try 块中 throw
            return new BusinessException(sb.toString());

        } catch (Exception e) {
            // JSON 解析失败，可能是 HTML 或其他格式
            log.error("Feign 响应解析失败，methodKey: {}, status: {}, body: {}", methodKey, response.status(), body);

            String errorMessage = String.format("服务调用失败 [%s] - HTTP %d", methodKey, response.status());

            // 如果是 404，提供更具体的错误信息
            if (response.status() == 404) {
                errorMessage = String.format("接口不存在 [%s] - 请检查服务是否正确启动和路径配置", methodKey);
            }

            return new BusinessException(errorMessage);
        }
    }

    private String extractBody(Response response) {
        try {
            if (response.body() != null) {
                // 使用Feign内置工具类读取响应体
                return Util.toString(response.body().asReader(Util.UTF_8));
            }
        } catch (IOException e) {
            log.error("feign提取body内容错误", e);
        }

        Map<String, String> map = new HashMap<>();
        map.put("message", "feign调用错误");
        return JSONObject.toJSONString(map);
    }
}