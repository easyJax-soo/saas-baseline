package com.baseline.system.controller.admin;

import com.baseline.common.controller.AdminController;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysMemberRealNameAuthAuditDTO;
import com.baseline.system.dto.SysMemberRealNameAuthDetailDTO;
import com.baseline.system.entity.SysMemberRealNameAuth;
import com.baseline.system.service.ISysMemberRealNameAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

@Api(tags = "[admin]-会员实名认证管理")
@RestController
@RequestMapping("/memberRealNameAuth")
public class SysMemberRealNameAuthController extends AdminController {

    @Resource
    private ISysMemberRealNameAuthService memberRealNameAuthService;



    @ApiOperation("审核实名认证")
    @Log(title = "会员实名认证-审核")
    @SaAdminCheckPermission("system:memberRealNameAuth:audit")
    @PostMapping("/audit")
    public boolean audit(@Valid @RequestBody SysMemberRealNameAuthAuditDTO dto) {
        return memberRealNameAuthService.auditAuth(dto);
    }

    @ApiOperation("获取会员认证详情")
    @Log(title = "会员实名认证-详情")
    @SaAdminCheckPermission("system:memberRealNameAuth:query")
    @PostMapping("/detail")
    public SysMemberRealNameAuth detail(@Valid @RequestBody SysMemberRealNameAuthDetailDTO dto) {
        return memberRealNameAuthService.getByMemberId(dto.getMemberId());
    }


}
