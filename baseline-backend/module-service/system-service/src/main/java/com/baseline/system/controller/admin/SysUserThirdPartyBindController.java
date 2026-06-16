package com.baseline.system.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baseline.common.controller.AdminController;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysUserThirdPartyBindFilterDTO;
import com.baseline.system.dto.SysUserThirdPartyUnbindDTO;
import com.baseline.system.service.ISysUserThirdPartyBindService;
import com.baseline.system.vo.SysUserThirdPartyBindVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

/**
 * 用户第三方绑定管理
 * 
 * @author system
 */
@Api(tags = "[admin]-用户第三方绑定管理")
@RestController
@RequestMapping("/userThirdPartyBind")
public class SysUserThirdPartyBindController extends AdminController {

    @Resource
    private ISysUserThirdPartyBindService sysUserThirdPartyBindService;

    @ApiOperation("用户第三方绑定分页")
    @Log(title = "用户第三方绑定-分页")
    @SaAdminCheckPermission("system:userThirdPartyBind:page")
    @PostMapping("/page")
    public IPage<SysUserThirdPartyBindVO> page(@RequestBody SysUserThirdPartyBindFilterDTO filterDTO) {
        return sysUserThirdPartyBindService.pageBindList(filterDTO);
    }

    @ApiOperation("解绑用户第三方账号")
    @Log(title = "用户第三方绑定-解绑")
    @SaAdminCheckPermission("system:userThirdPartyBind:unbind")
    @PostMapping("/unbind")
    public boolean unbind(@Valid @RequestBody SysUserThirdPartyUnbindDTO unbindDTO) {
        return sysUserThirdPartyBindService.unbindThirdPartyUser(unbindDTO);
    }
}
