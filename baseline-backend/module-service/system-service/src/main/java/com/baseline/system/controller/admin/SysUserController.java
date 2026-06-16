package com.baseline.system.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baseline.common.controller.AdminController;
import com.baseline.common.dto.SysUserFilterBizDTO;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.*;
import com.baseline.system.dto.SysUserDetailDTO;
import com.baseline.system.dto.UserTenantSwitchDTO;
import com.baseline.system.service.ISysUserService;
import com.baseline.system.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

@Api(tags = "[admin]-系统用户")
@RestController
@RequestMapping("/user")
public class SysUserController extends AdminController {

    @Resource
    private ISysUserService sysUserService;

    @ApiOperation("系统用户分页")
    @Log(title = "系统用户-分页")
    @SaAdminCheckPermission("system:user:page")
    @PostMapping("/page")
    public IPage<PageSysUserVO> page(@RequestBody SysUserFilterDTO dto) {
        return sysUserService.pageUser(dto);
    }

    @ApiOperation("新增编辑系统用户")
    @Log(title = "系统用户-保存")
    @SaAdminCheckPermission("system:user:save")
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@Valid @RequestBody SysUserSaveDTO dto) {
        return sysUserService.saveOrUpdateUser(dto);
    }

    @ApiOperation("系统用户详情")
    @Log(title = "系统用户-详情")
    @SaAdminCheckPermission("system:user:detail")
    @PostMapping("/detail")
    public SysUserDetailVO detail(@RequestBody SysUserDetailDTO dto) {
        return sysUserService.detail(dto);
    }


    @ApiOperation("批量删除系统用户")
    @Log(title = "系统用户-删除")
    @SaAdminCheckPermission("system:user:delete")
    @PostMapping("/remove")
    public boolean remove(@RequestBody List<Long> ids) {
        return sysUserService.removeSysRole(ids);
    }

    @ApiOperation("修改密码")
    @Log(title = "系统用户-修改密码")
    @SaAdminCheckPermission("system:user:changePassword")
    @PostMapping("/changePw")
    public boolean reset(@RequestBody SysUserChangePasswordDTO dto) {
        return sysUserService.changePw(dto);
    }

    @ApiOperation("重置密码")
    @Log(title = "系统用户-重置密码")
    @SaAdminCheckPermission("system:user:resetPassword")
    @PostMapping("/resetPw")
    public boolean resetPw(@RequestBody SysUserResetPasswordDTO dto) {
        return sysUserService.resetPw(dto);
    }

    @ApiOperation("我的用户信息")
    @Log(title = "系统用户-我的用户信息")
    @GetMapping("/info")
    public MyUserDetailVO getMyDetail() {
        return sysUserService.getMyDetail();
    }


    @ApiOperation("更新个人信息")
    @Log(title = "系统用户-更新个人信息")
    @SaAdminCheckPermission("system:user:setInfo")
    @PostMapping("/setInfo")
    public boolean setMyUserInfo(@RequestBody UserInfoDTO dto) {
        return sysUserService.setMyUserInfo(dto);
    }






    @ApiOperation("获取用户可访问租户列表")
    @Log(title = "用户租户-获取可访问租户列表")
    @PostMapping("/myTenantList")
    public List<UserTenantVO> getUserTenantList() {
        return sysUserService.getUserTenantList();
    }

    @ApiOperation("用户租户切换")
    @Log(title = "用户租户-租户切换")
    @PostMapping("/switchTenant")
    public boolean switchTenant(@Valid @RequestBody UserTenantSwitchDTO dto) {
        return sysUserService.switchTenant(dto);
    }

    @ApiOperation("获取用户简单列表")
    @PostMapping("/simpleList")
    public List<SysUserVO> simpleList(@RequestBody SysUserFilterBizDTO dto) {
        // 转换 DTO
        SysUserFilterDTO systemDto = new SysUserFilterDTO();
        BeanUtils.copyProperties(dto, systemDto);

        // 调用服务方法并转换结果
        List<SysUserVO> systemUserList = sysUserService.getSimpleList(systemDto);

        // 转换为 common 包的 SysUserVO
        return systemUserList.stream().map(systemUser -> {
            SysUserVO userVO = new SysUserVO();
            BeanUtils.copyProperties(systemUser, userVO);
            return userVO;
        }).collect(java.util.stream.Collectors.toList());
    }


}
