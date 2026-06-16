package com.baseline.system.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysTenantUserDetailDTO;
import com.baseline.system.dto.SysTenantUserFilterDTO;
import com.baseline.system.dto.SysTenantUserBindDTO;
import com.baseline.system.dto.SysTenantUserUnbindDTO;
import com.baseline.system.dto.SysTenantUnboundUserFilterDTO;
import com.baseline.system.service.ISysTenantUserService;
import com.baseline.system.vo.SysTenantUserDetailVO;
import com.baseline.system.vo.SysTenantUnboundUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

@Api(tags = "[admin]-系统租户用户")
@RestController
@RequestMapping("/tenant/user")
public class SysTenantUserController {

    @Resource
    private ISysTenantUserService sysTenantUserService;

    @ApiOperation("系统租户用户分页")
    @Log(title = "系统租户用户-分页")
    @SaAdminCheckPermission("system:tenantUser:page")
    @PostMapping("/page")
    public IPage<SysTenantUserDetailVO> page(@RequestBody SysTenantUserFilterDTO dto) {
        return sysTenantUserService.pageTenantUser(dto);
    }

    @ApiOperation("系统租户用户详情")
    @Log(title = "系统租户用户-详情")
    @SaAdminCheckPermission("system:tenantUser:detail")
    @PostMapping("/detail")
    public SysTenantUserDetailVO detail(@RequestBody SysTenantUserDetailDTO dto) {
        return sysTenantUserService.detail(dto);
    }

    @ApiOperation("绑定现有用户到租户")
    @Log(title = "系统租户用户-绑定用户")
    @SaAdminCheckPermission("system:tenantUser:bind")
    @PostMapping("/bindUser")
    public boolean bindUser(@Valid @RequestBody SysTenantUserBindDTO dto) {
        return sysTenantUserService.bindUserToTenant(dto);
    }

    @ApiOperation("解绑租户用户")
    @Log(title = "系统租户用户-解绑用户")
    @SaAdminCheckPermission("system:tenantUser:unbind")
    @PostMapping("/unbind")
    public boolean unbind(@Valid @RequestBody SysTenantUserUnbindDTO dto) {
        return sysTenantUserService.unbindTenantUsers(dto);
    }

    @ApiOperation("获取租户未绑定的用户列表")
    @Log(title = "系统租户用户-未绑定用户列表")
    @SaAdminCheckPermission("system:tenantUser:unboundUsers")
    @PostMapping("/unboundUsers")
    public IPage<SysTenantUnboundUserVO> getUnboundUsers(@Valid @RequestBody SysTenantUnboundUserFilterDTO dto) {
        return sysTenantUserService.getUnboundUsers(dto);
    }
}
