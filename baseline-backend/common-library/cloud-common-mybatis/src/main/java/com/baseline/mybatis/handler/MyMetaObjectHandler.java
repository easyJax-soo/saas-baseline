package com.baseline.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baseline.utils.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis-plus 自动填充 字段
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = getSafeUserId();
        String username = getSafeUsername();

        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);

        this.setFieldValByName("createUserId", userId, metaObject);
        this.setFieldValByName("updateUserId", userId, metaObject);

        this.setFieldValByName("createUser", username, metaObject);
        this.setFieldValByName("updateUser", username, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = getSafeUserId();
        String username = getSafeUsername();

        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateUserId", userId, metaObject);
        this.setFieldValByName("updateUser", username, metaObject);
    }

    /**
     * 安全获取用户ID，在非Web上下文（如CommandLineRunner启动时）返回null
     */
    private Long getSafeUserId() {
        try {
            return SecurityUtils.getUserId();
        } catch (Exception e) {
            log.debug("【自动填充】获取用户ID失败，跳过用户字段填充: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 安全获取用户名，在非Web上下文（如CommandLineRunner启动时）返回null
     */
    private String getSafeUsername() {
        try {
            return SecurityUtils.getUsername();
        } catch (Exception e) {
            log.debug("【自动填充】获取用户名失败，跳过用户字段填充: {}", e.getMessage());
            return null;
        }
    }
}
