package com.baseline.auth.provider.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthHandlerRegistry {

    private final Map<String, AuthenticationHandler> authHandlerMap = new HashMap<>();

    private final List<AuthenticationHandler> handlers; // 使用通配符接收所有类型的处理器

    @Autowired
    public AuthHandlerRegistry(List<AuthenticationHandler> handlers) {
        this.handlers = handlers;
    }

    @PostConstruct
    public void init() {
        for (AuthenticationHandler handler : handlers) {
            authHandlerMap.put(handler.getAuthType(), handler);
        }
    }

    public AuthenticationHandler getHandler(String authType) {
        return authHandlerMap.get(authType);
    }
}