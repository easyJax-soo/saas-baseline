package com.baseline.utils.security;

import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;

/**
 * StpLogic 门面类，管理项目中所有的 StpLogic 账号体系
 */
public class SaTokenUtils {

    /**
     * 管理后台登录标识
     */
    public static final String LOGIN_ADMIN = "admin";
    /**
     * member用户管理标识
     */
    public static final String LOGIN_MEMBER = "member";

    /**
     * 默认原生会话对象
     */
    public static final StpLogic DEFAULT = StpUtil.stpLogic;

    /**
     * Admin 会话对象，管理 sys_user 表所有账号的登录、权限认证
     */
    public static final StpLogic ADMIN = new StpLogic(LOGIN_ADMIN);

    /**
     * member 会话对象，管理 sys_member 表所有账号的登录、权限认证
     */
    public static final StpLogic MEMBER = new StpLogic(LOGIN_MEMBER);

}
