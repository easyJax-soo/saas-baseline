package com.baseline.common.security.annotation;

import cn.dev33.satoken.annotation.handler.SaAnnotationHandlerInterface;
import cn.dev33.satoken.annotation.handler.SaCheckPermissionHandler;
import com.baseline.common.constant.CommonConstants;
import com.baseline.utils.security.SaTokenUtils;
import com.baseline.utils.security.SecurityUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedElement;

/**
 * 注解 SaAdminPermissionHandler 的处理器
 */
@Component
public class SaAdminPermissionHandler implements SaAnnotationHandlerInterface<SaAdminCheckPermission> {

    @Override
    public Class<SaAdminCheckPermission> getHandlerAnnotationClass() {
        return SaAdminCheckPermission.class;
    }

    @Override
    public void checkMethod(SaAdminCheckPermission at, AnnotatedElement element) {
        //  判断是否为超级管理员: 用户ID为1
        boolean isSuperAdmin = SecurityUtils.isAdmin(SecurityUtils.getLoginUser());

        if (isSuperAdmin) {
            // 超级管理员跳过所有权限校验
            return;
        }

        // 3. 非超级管理员执行正常的权限校验
        SaCheckPermissionHandler._checkMethod(SaTokenUtils.ADMIN.getLoginType(), at.value(), at.mode(), at.orRole());
    }
}