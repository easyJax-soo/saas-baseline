package com.baseline.system.controller.admin;

import com.baseline.common.controller.AdminController;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysUserRealNameAuthAuditDTO;
import com.baseline.system.dto.SysUserRealNameAuthDetailDTO;
import com.baseline.system.service.ISysUserRealNameAuthService;
import com.baseline.system.vo.SysUserRealNameAuthVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * <p>
 * 用户实名认证信息表 前端控制器
 * </p>
 *
 * @author system
 * @since 2024-01-01
 */
@Api(tags = "[admin]-用户实名认证管理")
@RestController
@RequestMapping("/userRealNameAuth")
public class SysUserRealNameAuthController extends AdminController {

    @Autowired
    private ISysUserRealNameAuthService userRealNameAuthService;



    @ApiOperation("获取用户实名认证详情")
    @Log(title = "用户实名认证-获取详情")
    @SaAdminCheckPermission("system:userRealNameAuth:detail")
    @PostMapping("/detail")
    public SysUserRealNameAuthVO detail(@Valid @RequestBody SysUserRealNameAuthDetailDTO detailDTO) {
        return userRealNameAuthService.getUserAuth(detailDTO.getUserId());
    }


    @ApiOperation("审核用户实名认证")
    @Log(title = "用户实名认证-审核")
    @SaAdminCheckPermission("system:userRealNameAuth:audit")
    @PostMapping("/audit")
    public boolean audit(@Valid @RequestBody SysUserRealNameAuthAuditDTO auditDTO) {
        return userRealNameAuthService.auditAuth(auditDTO);
    }


}
