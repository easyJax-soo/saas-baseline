package com.baseline.common.security.annotation;

import cn.dev33.satoken.annotation.handler.SaAnnotationHandlerInterface;
import cn.dev33.satoken.annotation.handler.SaCheckLoginHandler;
import com.baseline.utils.security.SaTokenUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedElement;

/**
 * 注解 SaAdminCheckLoginHandler 的处理器
 */
@Component
public class SaAdminCheckLoginHandler implements SaAnnotationHandlerInterface<SaAdminCheckLogin> {

    @Override
    public Class<SaAdminCheckLogin> getHandlerAnnotationClass() {
        return SaAdminCheckLogin.class;
    }

    @Override
    public void checkMethod(SaAdminCheckLogin at, AnnotatedElement element) {
        SaCheckLoginHandler._checkMethod(SaTokenUtils.ADMIN.getLoginType());
    }

}
