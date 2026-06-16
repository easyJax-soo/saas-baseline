package com.baseline.common.constant;

public class CommonConstants {

    /**
     * 超级管理员 ID,用于判断是否为超级管理员
     */
    public static final Long SUPER_ADMIN_USER_ID = 1L;

    /**
     * 启用状态
     */
    public static final Integer SYS_ENABLE = 1;

    /**
     * 是否删除
     */
    public static final boolean SYS_DELETE = false;

    /**
     * 未删除状态
     */
    public static final Integer SYS_NOT_DELETED = 0;

    /**
     * 已删除状态
     */
    public static final Integer SYS_DELETED = 1;

    /**
     * 系统默认配置
     */
    public static final Integer SYS_DEFAULT = 1;

    /**
     * 字典默认值
     */
    public static final String DICT_DEFAULT_TEXT = "未配置字典";


    /**
     * 请求头服务名
     */
    public static final String HEADER_SERVICE_PREFIX = "Hc-Service-Prefix";

    /**
     * 请求头接口前缀
     */
    public static final String HEADER_API_PREFIX = "Hc-Api-Prefix";

    /**
     * 认证服务名称
     */
    public static final String AUTH_SERVICE = "auth";

    /**
     * feign API调用前缀
     */
    public static final String FEIGN_API_PREFIX = "feignApi";

    /**
     * admin API调用前缀
     */
    public static final String ADMIN_API_PREFIX = "adminApi";

    /**
     * web API调用前缀
     */
    public static final String WEB_API_PREFIX = "webApi";

    /**
     * Sa-Token缓存键前缀
     */
    public static final String SATOKEN_CACHE_PREFIX = "satoken:";
    
    /**
     * Sa-Token角色缓存键前缀
     */
    public static final String SATOKEN_ROLE_CACHE_PREFIX = "satoken:loginId-find-role:";

    /**
     * Sa-Token权限缓存键前缀
     */
    public static final String SATOKEN_PERMISSION_CACHE_PREFIX = "satoken:loginId-find-permission:";

    /**
     * Sa-Token项目权限缓存键前缀
     */
    public static final String SATOKEN_PROJECT_CACHE_PREFIX = "satoken:loginId-find-project:";

    /**
     * Sa-Token缓存过期时间（秒）- 30天
     */
    public static final long SATOKEN_CACHE_EXPIRE = 60 * 60 * 24 * 30L;

    /**
     * 租户切换接口路径
     */
    public static final String SWITCH_TENANT_PATH = "/user/switchTenant";

    /**
     * 字典数据缓存键前缀
     */
    public static final String DICT_DATA_CACHE_PREFIX = "dict:data:";

    /**
     * 字典数据缓存过期时间（秒）- 24小时
     */
    public static final long DICT_DATA_CACHE_EXPIRE = 24 * 60 * 60L;


}
