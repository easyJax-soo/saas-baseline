package com.baseline.system.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.*;
import com.baseline.system.service.ISysTenantService;
import com.baseline.system.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

@Api(tags = "[admin]-系统租户")
@RestController
@RequestMapping("/tenant")
public class SysTenantController {

    @Resource
    private ISysTenantService sysTenantService;

    @ApiOperation("系统租户分页")
    @Log(title = "系统租户-租户分页")
    @SaAdminCheckPermission("system:tenant:page")
    @PostMapping("/page")
    public IPage<SysTenantVO> page(@RequestBody SysTenantFilterDTO dto) {
        return sysTenantService.pageTenant(dto);
    }

    @ApiOperation("新增编辑系统租户")
    @Log(title = "系统租户-保存")
    @SaAdminCheckPermission("system:tenant:save")
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@Valid @RequestBody SysTenantSaveDTO dto) {
        return sysTenantService.saveOrUpdate(dto);
    }

    @ApiOperation("系统租户详情")
    @Log(title = "系统租户-详情")
    @SaAdminCheckPermission("system:tenant:detail")
    @PostMapping("/detail")
    public SysTenantVO detail(@RequestBody SysTenantDetailDTO dto) {
        return sysTenantService.detail(dto);
    }


    @ApiOperation("批量删除系统租户")
    @Log(title = "系统租户-删除")
    @SaAdminCheckPermission("system:tenant:delete")
    @PostMapping("/remove")
    public boolean remove(@RequestBody List<Long> ids) {
        return sysTenantService.remove(ids);
    }

    @ApiOperation("获取租户资源详情（权限、菜单、项目）")
    @Log(title = "系统租户-获取资源详情")
    @SaAdminCheckPermission("system:tenant:resource:detail")
    @PostMapping("/resource/detail")
    public SysTenantResourceVO getTenantResource(@RequestBody SysTenantResourceDTO dto) {
        return sysTenantService.getTenantResource(dto);
    }

    @ApiOperation("保存租户资源（权限、菜单、项目）")
    @Log(title = "系统租户-保存资源")
    @SaAdminCheckPermission("system:tenant:resource:save")
    @PostMapping("/resource/saveOrUpdate")
    public boolean saveTenantResource(@Valid @RequestBody SysTenantResourceSaveDTO dto) {
        return sysTenantService.saveTenantResource(dto);
    }

    // ========== 层级租户相关接口 ==========

    @ApiOperation("获取租户树形结构")
    @Log(title = "系统租户-树形结构")
    @SaAdminCheckPermission("system:tenant:tree")
    @PostMapping("/tree")
    public List<SysTenantTreeVO> getTenantTree(@RequestBody SysTenantTreeDTO dto) {
        return sysTenantService.getTenantTree(dto.getParentId());
    }

    @ApiOperation("获取租户详情（包含层级信息）")
    @Log(title = "系统租户-层级详情")
    @SaAdminCheckPermission("system:tenant:hierarchy:detail")
    @PostMapping("/hierarchy/detail")
    public SysTenantDetailVO getTenantDetail(@Valid @RequestBody SysTenantHierarchyDetailDTO dto) {
        return sysTenantService.getTenantDetail(dto.getId());
    }
}
