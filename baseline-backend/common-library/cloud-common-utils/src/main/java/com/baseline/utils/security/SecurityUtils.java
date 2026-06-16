package com.baseline.utils.security;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.SecureUtil;
import com.baseline.common.constant.CommonConstants;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.core.exception.BusinessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SecurityUtils {

    public static LoginUserBizVO getLoginUser() {
        String token = getToken();
        Map<String, Object> loginUserMap = new HashMap<>();

        if (SaTokenUtils.ADMIN.isLogin()) {
            Object adminUserId = SaTokenUtils.ADMIN.getLoginIdByToken(token);
            loginUserMap = SaTokenUtils.ADMIN.getSessionByLoginId(adminUserId).getDataMap();

        }else if (SaTokenUtils.MEMBER.isLogin()) {
            Object memberUserId = SaTokenUtils.MEMBER.getLoginIdByToken(token);
            loginUserMap = SaTokenUtils.MEMBER.getSessionByLoginId(memberUserId).getDataMap();
        }

//        if(loginUserMap.isEmpty()){
//            throw new NotLoginException(NotLoginException.INVALID_TOKEN_MESSAGE, SaTokenUtils.LOGIN_ADMIN, NotLoginException.INVALID_TOKEN);
//        }
        return BeanUtil.copyProperties(loginUserMap, LoginUserBizVO.class);
    }

    public static String getUsername(){
        return getLoginUser().getUsername();
    }

    public static Long getUserId(){
        return getLoginUser().getId();
    }

    public static Boolean isAdmin(LoginUserBizVO loginUser){
        // 如果已经切换到租户环境，则不再是超级管理员
        Long tenantId = getTenantId();
        if (tenantId != null && tenantId > 0) {
            return false;
        }
        
        // 只有在非租户环境下，才判断是否为超级管理员
        return CommonConstants.SUPER_ADMIN_USER_ID.equals(loginUser.getId()) && SaTokenUtils.LOGIN_ADMIN.equals(loginUser.getLoginType());
    }

    public static Long getTenantId(){
        try {
            // 从session中获取租户ID
            Map<String, Object> sessionData = null;
            if (SaTokenUtils.ADMIN.isLogin()) {
                sessionData = SaTokenUtils.ADMIN.getSession().getDataMap();
            } else if (SaTokenUtils.MEMBER.isLogin()) {
                sessionData = SaTokenUtils.MEMBER.getSession().getDataMap();
            }
            
            if (sessionData != null && sessionData.containsKey("tenantId")) {
                Object tenantId = sessionData.get("tenantId");
                if (tenantId instanceof Long) {
                    return (Long) tenantId;
                } else if (tenantId instanceof Integer) {
                    return ((Integer) tenantId).longValue();
                } else if (tenantId instanceof String) {
                    return Long.parseLong((String) tenantId);
                }
            }
            
            return 0L; // 默认返回0，表示没有租户或超级管理员
        } catch (Exception e) {
            return 0L; // 异常情况下返回0
        }
    }

    /**
     * 判断当前用户是否为租户管理员
     * 
     * @return true-是租户管理员，false-不是租户管理员
     */
    public static Boolean isTenantAdmin(){
        try {
            // 从session中获取租户管理员状态
            Map<String, Object> sessionData = null;
            if (SaTokenUtils.ADMIN.isLogin()) {
                sessionData = SaTokenUtils.ADMIN.getSession().getDataMap();
            } else if (SaTokenUtils.MEMBER.isLogin()) {
                sessionData = SaTokenUtils.MEMBER.getSession().getDataMap();
            }
            
            if (sessionData != null && sessionData.containsKey("isTenantAdmin")) {
                Object isTenantAdmin = sessionData.get("isTenantAdmin");
                if (isTenantAdmin instanceof Boolean) {
                    return (Boolean) isTenantAdmin;
                } else if (isTenantAdmin instanceof String) {
                    return Boolean.parseBoolean((String) isTenantAdmin);
                } else if (isTenantAdmin instanceof Integer) {
                    return ((Integer) isTenantAdmin) == 1;
                }
            }
            
            return false; // 默认返回false
        } catch (Exception e) {
            return false; // 异常情况下返回false
        }
    }

    /**
     * 获取层级租户ID列表（包含当前租户及其所有子租户）
     * 在用户登录时预计算并存储在session中
     * 
     * @return 层级租户ID列表
     */
    @SuppressWarnings("unchecked")
    public static List<Long> getHierarchyTenantIds(){
        try {
            // 从session中获取层级租户ID列表
            Map<String, Object> sessionData = null;
            if (SaTokenUtils.ADMIN.isLogin()) {
                sessionData = SaTokenUtils.ADMIN.getSession().getDataMap();
            } else if (SaTokenUtils.MEMBER.isLogin()) {
                sessionData = SaTokenUtils.MEMBER.getSession().getDataMap();
            }
            
            if (sessionData != null && sessionData.containsKey("hierarchyTenantIds")) {
                Object hierarchyTenantIds = sessionData.get("hierarchyTenantIds");
                if (hierarchyTenantIds instanceof List) {
                    return (List<Long>) hierarchyTenantIds;
                }
            }
            
            return null; // 没有层级租户ID列表
        } catch (Exception e) {
            return null; // 异常情况下返回null
        }
    }


    public static String getToken(){
        return StpUtil.getTokenValue();
    }

    /**
     * 生成BCrypt加密密码（使用随机盐）
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password)
    {
        if (StrUtil.isEmpty(password)) {
            return null;
        }
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * 生成BCrypt加密密码（使用指定盐值）
     *
     * @param password 密码
     * @param salt 盐值（6位字符串）
     * @return 加密字符串
     */
    public static String encryptPassword(String password, String salt)
    {
        if (StrUtil.isEmpty(password) || StrUtil.isEmpty(salt)) {
            return null;
        }
        // 将6位盐值转换为BCrypt格式的盐值
        String bcryptSalt = generateBCryptSalt(salt);
        return BCrypt.hashpw(password, bcryptSalt);
    }

    /**
     * 将6位字符串盐值转换为BCrypt格式的盐值
     * 
     * @param salt 6位字符串盐值
     * @return BCrypt格式的盐值
     */
    private static String generateBCryptSalt(String salt) {
        // BCrypt盐值格式: $2a$10$[22字符的base64编码盐值]
        // 我们使用固定的cost factor (10) 和基于输入盐值生成的22字符base64字符串
        
        // 将6位盐值扩展到22位，使用MD5哈希并截取
        String expandedSalt = SecureUtil.md5(salt + "BCryptSaltExpansion").substring(0, 22);
        
        // 确保字符串符合BCrypt base64字符集 [./A-Za-z0-9]
        expandedSalt = expandedSalt.replaceAll("[^A-Za-z0-9]", ".")
                                 .substring(0, 22);
        
        // 如果长度不足22位，用'.'补齐
        while (expandedSalt.length() < 22) {
            expandedSalt += ".";
        }
        
        return "$2a$10$" + expandedSalt;
    }

    /**
     * 判断密码是否相同（BCrypt方式）
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        if (StrUtil.isEmpty(rawPassword) || StrUtil.isEmpty(encodedPassword)) {
            return false;
        }
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }

    /**
     * 判断密码是否相同（BCrypt+指定盐值方式）
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @param salt 盐值（6位字符串）
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword, String salt)
    {
        if (StrUtil.isEmpty(rawPassword) || StrUtil.isEmpty(encodedPassword) || StrUtil.isEmpty(salt)) {
            return false;
        }
        // 使用相同的盐值重新加密，然后比较结果
        String reEncrypted = encryptPassword(rawPassword, salt);
        return encodedPassword.equals(reEncrypted);
    }

    /**
     * 密码强度验证
     * @param password 密码
     * @throws BusinessException 密码不符合要求时抛出异常
     */
    public static void passwordValidator(String password) {
        // 定义密码的正则表达式，包含至少一个大写字母、小写字母、数字、特殊字符，并且长度至少为8
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);

        boolean result = pattern.matcher(password).matches();
        if (!result) {
            throw new BusinessException("密码需要包含至少一个大写字母、小写字母、数字、特殊字符，并且长度至少为8");
        }
    }
}
