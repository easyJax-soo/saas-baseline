package com.baseline.system.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baseline.common.controller.AdminController;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysMemberThirdPartyBindFilterDTO;
import com.baseline.system.dto.SysMemberThirdPartyUnbindDTO;
import com.baseline.system.service.ISysMemberThirdPartyBindService;
import com.baseline.system.vo.SysMemberThirdPartyBindVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

/**
 * 会员第三方绑定管理
 * 
 * @author system
 */
@Api(tags = "[admin]-会员第三方绑定管理")
@RestController
@RequestMapping("/memberThirdPartyBind")
public class SysMemberThirdPartyBindController extends AdminController {

    @Resource
    private ISysMemberThirdPartyBindService sysMemberThirdPartyBindService;

    @ApiOperation("会员第三方绑定分页")
    @Log(title = "会员第三方绑定-分页")
    @SaAdminCheckPermission("system:memberThirdPartyBind:page")
    @PostMapping("/page")
    public IPage<SysMemberThirdPartyBindVO> page(@RequestBody SysMemberThirdPartyBindFilterDTO filterDTO) {
        return sysMemberThirdPartyBindService.pageBindList(filterDTO);
    }

    @ApiOperation("解绑会员第三方账号")
    @Log(title = "会员第三方绑定-解绑")
    @SaAdminCheckPermission("system:memberThirdPartyBind:unbind")
    @PostMapping("/unbind")
    public boolean unbind(@Valid @RequestBody SysMemberThirdPartyUnbindDTO unbindDTO) {
        return sysMemberThirdPartyBindService.unbindThirdPartyMember(unbindDTO);
    }
}
