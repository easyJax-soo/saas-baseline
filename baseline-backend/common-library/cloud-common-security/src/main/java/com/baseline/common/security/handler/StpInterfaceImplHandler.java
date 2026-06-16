package com.baseline.common.security.handler;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.util.ObjectUtil;
import com.baseline.common.constant.CommonConstants;
import com.baseline.common.dto.UserPermissionBizDTO;
import com.baseline.common.dto.UserRoleBizDTO;
import com.baseline.common.service.ISysUserPermissionBizService;
import com.baseline.common.service.ISysUserRoleBizService;
import com.baseline.common.vo.UserPermissionBizVO;
import com.baseline.common.vo.UserRoleBizVO;
import com.baseline.common.constant.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImplHandler implements StpInterface {

    @Autowired
    ISysUserRoleBizService sysUserRoleBizService;
    @Autowired
    ISysUserPermissionBizService sysUserPermissionBizService;

    /**
     * 返回一个账号所拥有的权限码集合
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 先尝试从缓存获取权限列表
        List<String> permissionList = (List<String>) SaManager.getSaTokenDao().getObject(CommonConstants.SATOKEN_PERMISSION_CACHE_PREFIX + loginId);
        
        if (permissionList == null) {
            try {
                // 1. 获取用户角色列表
                List<UserRoleBizVO> roles = getUserRoleList(loginId, loginType);
                List<Long> roleIdList = roles.stream().map(UserRoleBizVO::getId).collect(Collectors.toList());
                
                // 2. 调用Service获取权限（支持多角色）
                UserPermissionBizDTO dto = new UserPermissionBizDTO();
                dto.setUserId(Long.valueOf(String.valueOf(loginId)));
                dto.setRoleIds(roleIdList); // 传递角色ID列表
                
                List<UserPermissionBizVO> permissions = sysUserPermissionBizService.getPermissionsByUserIdAndRoleId(dto, SecurityConstants.INNER);
                permissionList = permissions.stream().map(UserPermissionBizVO::getPermission).collect(Collectors.toList());
                
                // 3. 将权限列表存入缓存
                if (ObjectUtil.isNotEmpty(permissionList)) {
                    SaManager.getSaTokenDao().setObject(CommonConstants.SATOKEN_PERMISSION_CACHE_PREFIX + loginId, permissionList, CommonConstants.SATOKEN_CACHE_EXPIRE);
                }
                
            } catch (Exception e) {
                // 如果出现异常（如用户认证失败），返回空权限列表
                permissionList = new ArrayList<>();
            }
        }
        
        return permissionList;
    }


    /**
     * 返回一个账号所拥有的角色标识集合
     *
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roleList = (List<String>)SaManager.getSaTokenDao().getObject(CommonConstants.SATOKEN_ROLE_CACHE_PREFIX + loginId);
        if(roleList == null) {
            List<UserRoleBizVO> roles = getUserRoleList(loginId, loginType);
            roleList = roles.stream().map(UserRoleBizVO::getKey).collect(Collectors.toList());

            if(ObjectUtil.isNotEmpty(roleList)){
                SaManager.getSaTokenDao().setObject(CommonConstants.SATOKEN_ROLE_CACHE_PREFIX + loginId, roleList, CommonConstants.SATOKEN_CACHE_EXPIRE);
            }
        }
        return roleList;
    }

    /**
     * 获取用户角色列表
     * @param loginId
     * @param loginType
     * @return
     */
    private List<UserRoleBizVO> getUserRoleList(Object loginId, String loginType){
        UserRoleBizDTO dto = new UserRoleBizDTO();
        dto.setUserId(Long.valueOf(String.valueOf(loginId)));
        return sysUserRoleBizService.getRolesByUserId(dto, SecurityConstants.INNER);
    }

}
