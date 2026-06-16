package com.baseline.auth.provider.common;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.hutool.core.bean.BeanUtil;
import com.baseline.auth.vo.LoginTokenVO;
import com.baseline.common.constant.CommonConstants;
import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.service.ISysUserBizService;
import com.baseline.common.service.ISysMemberBizService;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.common.constant.SecurityConstants;
import com.baseline.core.exception.BusinessException;
import com.baseline.utils.security.SaTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public abstract class AuthenticationAbstractHandler implements AuthenticationHandler {
    @Autowired
    private ISysUserBizService sysUserBizService;
    
    @Autowired
    private ISysMemberBizService sysMemberBizService;

    /**
     * 加载用户信息
     */
    public LoginUserBizVO loadUserInfo(String type, String username){
        LoginUserBizDTO dto = new LoginUserBizDTO();
        dto.setUsername(username);

        LoginUserBizVO loginUserBizVO;
        
        // 根据登录类型选择不同的服务
        if (SaTokenUtils.LOGIN_ADMIN.equals(type)) {
            loginUserBizVO = sysUserBizService.getLoginUserByUsername(dto, SecurityConstants.INNER);
        } else if (SaTokenUtils.LOGIN_MEMBER.equals(type)) {
            loginUserBizVO = sysMemberBizService.getLoginUserByUsername(dto, SecurityConstants.INNER);
        } else {
            throw new BusinessException("不支持的登录类型: " + type);
        }
        
        if(!loginUserBizVO.getStatus().equals(CommonConstants.SYS_ENABLE)){
            throw new BusinessException("用户已被禁止登录");
        }

        return loginUserBizVO;
    }


    /**
     * 认证类型
     * @return
     */
    public LoginTokenVO login(LoginUserBizVO user, String loginType){
        @SuppressWarnings("unchecked")
        Map<String, Object> userMap = BeanUtil.copyProperties(user, Map.class);
        // 移除敏感字段，避免存储到会话中
        userMap.remove("password");
        userMap.remove("salt");
        SaTokenInfo saTokenInfo = null;
        switch (loginType) {
            case SaTokenUtils.LOGIN_ADMIN:
                SaTokenUtils.ADMIN.login(user.getId());
                saTokenInfo = SaTokenUtils.ADMIN.getTokenInfo();
                SaTokenUtils.ADMIN.getSession().refreshDataMap(userMap);
                break;
            case SaTokenUtils.LOGIN_MEMBER:
                SaTokenUtils.MEMBER.login(user.getId());
                saTokenInfo = SaTokenUtils.MEMBER.getTokenInfo();
                SaTokenUtils.MEMBER.getSession().refreshDataMap(userMap);
                break;
            default:
                // 处理不支持的登录类型，可以抛出异常或使用默认策略
                throw new BusinessException("不支持的登录类型: " + loginType);
        }

        LoginTokenVO tokenInfo = new LoginTokenVO();
        tokenInfo.setLoginType(saTokenInfo.getLoginType());
        tokenInfo.setToken(saTokenInfo.getTokenValue());
        tokenInfo.setExpired(saTokenInfo.getTokenTimeout());
        tokenInfo.setTokenPrefix(SaManager.getConfig().getTokenPrefix());
//        tokenInfo.setRoles(SaManager.getStpInterface().getRoleList(user.getId(), loginType));
//        tokenInfo.setPermissions(SaMan    ager.getStpInterface().getPermissionList(user.getId(), loginType));

        return tokenInfo;
    }

}