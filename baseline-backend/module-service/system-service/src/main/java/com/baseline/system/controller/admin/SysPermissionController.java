package com.baseline.system.controller.admin;


import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.vo.SysPermissionDetailVO;
import com.baseline.system.vo.SysPermissionNodeVO;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

import com.baseline.system.service.ISysPermissionService;
import com.baseline.system.dto.SysPermissionDetailDTO;
import com.baseline.system.dto.SysPermissionFilterDTO;
import com.baseline.system.dto.SysPermissionSaveDTO;



/**
 * 权限表 控制器
 **/

@Api(tags = "[admin]-权限表")
@RestController
@RequestMapping("/sysPermission")
public class SysPermissionController {

    @Resource
    ISysPermissionService sysPermissionService;



    @ApiOperation("树结构数据")
    @Log(title = "权限-树结构")
    @SaAdminCheckPermission("system:permission:tree")
    @PostMapping("/tree")
    public List<SysPermissionNodeVO> simpleList(@RequestBody SysPermissionFilterDTO dto) {
        return sysPermissionService.getNodeList(dto);
    }

    @ApiOperation("新增编辑")
    @Log(title = "权限-保存")
    @SaAdminCheckPermission("system:permission:save")
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@Valid @RequestBody SysPermissionSaveDTO dto) {
        return sysPermissionService.saveOrUpdate(dto);
    }

    @ApiOperation("详情")
    @Log(title = "权限-详情")
    @SaAdminCheckPermission("system:permission:detail")
    @PostMapping("/detail")
    public SysPermissionDetailVO detail(@RequestBody SysPermissionDetailDTO dto) {
        return sysPermissionService.getById(dto.getId());
    }

    @ApiOperation("删除")
    @Log(title = "权限-删除")
    @SaAdminCheckPermission("system:permission:delete")
    @PostMapping("/remove")
    public boolean remove(@RequestBody List<Long> ids) {
        return sysPermissionService.safeRemoveByIds(ids);
    }



}
