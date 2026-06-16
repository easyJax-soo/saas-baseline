package com.baseline.system.controller.admin;

import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysConfigGroupFilterDTO;
import com.baseline.system.dto.SysConfigGroupSaveDTO;
import com.baseline.system.vo.SysConfigGroupVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import com.baseline.system.service.ISysConfigGroupService;

import java.util.List;

/**
 * 系统配置分组 控制器
 **/

@Api(tags = "[admin]-系统配置分组")
@RestController
@RequestMapping("/sysConfigGroup")
@Validated
public class SysConfigGroupController {

    @Resource
    ISysConfigGroupService sysConfigGroupService;

    @ApiOperation("获取配置分组列表")
    @Log(title = "系统配置分组-获取列表")
    @SaAdminCheckPermission("system:config:group:list")
    @PostMapping("/list")
    public List<SysConfigGroupVO> list(@RequestBody SysConfigGroupFilterDTO dto) {
        return sysConfigGroupService.list(dto);
    }

    @ApiOperation("保存配置分组")
    @Log(title = "系统配置分组-保存")
    @SaAdminCheckPermission("system:config:group:save")
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@Valid @RequestBody SysConfigGroupSaveDTO dto) {
        return sysConfigGroupService.saveOrUpdate(dto);
    }

    @ApiOperation("删除配置分组")
    @Log(title = "系统配置分组-删除")
    @SaAdminCheckPermission("system:config:group:delete")
    @PostMapping("/remove")
    public boolean remove(@RequestBody List<Long> ids) {
        return sysConfigGroupService.removeBatchByIdsWithCheck(ids);
    }
}
