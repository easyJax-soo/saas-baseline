package com.baseline.system.controller.admin;


import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.entity.SysConfig;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import com.baseline.system.service.ISysConfigService;
import com.baseline.system.dto.SysConfigDetailDTO;
import com.baseline.system.dto.SysConfigFilterDTO;
import com.baseline.system.dto.SysConfigSaveDTO;
import  com.baseline.system.vo.SysConfigVO;

import java.util.List;
import java.util.Map;


/**
 * 系统配置 控制器
 **/

@Api(tags = "[admin]-系统配置")
@RestController
@RequestMapping("/sysConfig")
@Validated
public class SysConfigController {

    @Resource
    ISysConfigService sysConfigService;

    @ApiOperation("获取配置项列表")
    @Log(title = "系统配置-获取列表")
    @SaAdminCheckPermission("system:config:list")
    @PostMapping("/list")
    public List<SysConfigVO> list(@RequestBody SysConfigFilterDTO dto) {
        return sysConfigService.list(dto);
    }

    @ApiOperation("获取系统配置")
    @Log(title = "获取系统配置")
    @GetMapping("/all")
    public Map<String, String> getConfigAll() {
        return sysConfigService.getConfigAll();
    }



    @ApiOperation("保存")
    @Log(title = "系统配置-保存")
    @SaAdminCheckPermission("system:config:save")
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@Valid @RequestBody SysConfigSaveDTO  dto) {
        return sysConfigService.saveOrUpdate(dto);
    }

    @ApiOperation("详情")
    @Log(title = "系统配置-通过ID获取详情")
    @SaAdminCheckPermission("system:config:detail")
    @PostMapping("/detail")
    public SysConfig detail(@RequestBody SysConfigDetailDTO dto) {
        return sysConfigService.getById(dto.getId());
    }

    @ApiOperation("删除")
    @Log(title = "系统配置-删除")
    @SaAdminCheckPermission("system:config:delete")
    @PostMapping("/remove")
    public boolean remove(@RequestBody List<Long> ids) {
        return sysConfigService.removeBatchByIdsWithGroupCheck(ids);
    }


}
