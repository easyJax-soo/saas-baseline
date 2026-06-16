package com.baseline.system.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysRoleDetailDTO;
import com.baseline.system.dto.SysRoleFilterDTO;
import com.baseline.system.dto.SysRoleSaveDTO;
import com.baseline.system.service.ISysRoleService;
import com.baseline.system.vo.SimpleRoleVO;
import com.baseline.system.vo.SysRolePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Api(tags = "[admin]-系统角色")
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @ApiOperation("系统角色分页列表")
    @Log(title = "系统角色-分页列表")
    @SaAdminCheckPermission("system:role:page")
    @PostMapping("/page")
    public IPage<SysRolePageVO> rolePage(@RequestBody SysRoleFilterDTO dto) {
        return sysRoleService.pageRole(dto);
    }


    @ApiOperation("系统角色列表")
    @Log(title = "系统角色-列表")
    @GetMapping("/list")
    @SaAdminCheckPermission("system:role:list")
    public List<SimpleRoleVO> roleList() {
        return sysRoleService.getSimpleList();
    }



    @ApiOperation("新增编辑系统角色")
    @Log(title = "系统角色-保存分页")
    @SaAdminCheckPermission("system:role:save")
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@Valid @RequestBody SysRoleSaveDTO dto) {
        return sysRoleService.saveOrUpdateRole(dto);
    }

    @ApiOperation("系统角色详情")
    @Log(title = "系统角色-详情")
    @SaAdminCheckPermission("system:role:detail")
    @PostMapping("/detail")
    public SysRoleSaveDTO detail(@RequestBody SysRoleDetailDTO dto) {
        return sysRoleService.detail(dto);
    }


    @ApiOperation("批量删除系统角色")
    @Log(title = "系统角色-删除系统角色")
    @SaAdminCheckPermission("system:role:delete")
    @PostMapping("/remove")
    public boolean remove(@RequestBody List<Long> ids) {
        return sysRoleService.removeSysRole(ids);
    }



}
