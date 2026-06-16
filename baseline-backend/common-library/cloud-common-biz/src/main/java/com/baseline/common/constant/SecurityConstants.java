package com.baseline.common.constant;

/**
 * 权限相关通用常量
 *
 * @author ruoyi
 */
public class SecurityConstants
{
    /**
     * 用户ID字段
     */
    public static final String DETAILS_USER_ID = "user_id";

    /**
     * 用户名字段
     */
    public static final String DETAILS_USERNAME = "username";

    /**
     * 授权信息字段
     */
    public static final String AUTHORIZATION_HEADER = "authorization";

    /**
     * 请求来源
     */
    public static final String FROM_SOURCE = "from-source";

    /**
     * 内部请求
     */
    public static final String INNER = "inner";


    /**
     * 登录用户
     */
    public static final String LOGIN_USER = "login_user";

    /**
     * 角色权限
     */
    public static final String ROLE_PERMISSION = "role_permission";


    /**
     * 租户标识
     */
    public static final String TENANT = "tenant";


    public static final String X_ACCESS_KEY = "X-Access-Key";
    public static final String X_TIMESTAMP = "X-Timestamp";
    public static final String X_SIGN = "X-Sign";
    public static final Long X_EFFECTIVE_TIME = (long) (5 * 60 * 1000);


    public static final String LOGIN_USER_KEY = "admin_user";

}
