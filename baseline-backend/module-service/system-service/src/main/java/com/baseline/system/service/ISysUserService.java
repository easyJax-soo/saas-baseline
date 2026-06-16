package com.baseline.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.system.dto.*;
import com.baseline.system.dto.UserTenantSwitchDTO;
import com.baseline.system.entity.SysUser;
import com.baseline.system.vo.*;
import com.baseline.system.vo.UserTenantVO;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface ISysUserService extends IService<SysUser> {

    boolean saveOrUpdateUser(SysUserSaveDTO dto);

    SysUserDetailVO detail(SysUserDetailDTO dto);

    boolean removeSysRole(List<Long> ids);

    boolean setUserRole(UserRoleSaveDTO dto);

    MyUserDetailVO getMyDetail();

    boolean setMyUserInfo(UserInfoDTO dto);

    boolean changePw(SysUserChangePasswordDTO dto);

    boolean resetPw(SysUserResetPasswordDTO dto);

    List<SysUserVO> getSimpleList(SysUserFilterDTO dto);

    LoginUserBizVO getLoginUserByUsername(LoginUserBizDTO dto);

    IPage<PageSysUserVO> pageUser(SysUserFilterDTO dto);

    /**
     * 获取用户可访问的租户列表
     *
     * @return 用户可访问的租户列表
     */
    List<UserTenantVO> getUserTenantList();

    /**
     * 用户租户切换
     *
     * @param dto 切换信息
     * @return 是否成功
     */
    boolean switchTenant(UserTenantSwitchDTO dto);
}
