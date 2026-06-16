package com.baseline.system.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysDictDataDTO;
import com.baseline.system.dto.SysDictDataFilterDTO;
import com.baseline.system.dto.SysDictTypeSaveDTO;
import com.baseline.system.entity.SysDictType;
import com.baseline.system.service.ISysDictDataService;
import com.baseline.system.service.ISysDictTypeService;
import com.baseline.system.dto.SysDictGroupDTO;
import com.baseline.system.vo.SysDictGroupVO;
import com.baseline.system.vo.SysDictVO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import jakarta.annotation.Resource;

import java.util.List;

@Slf4j
@Api(tags = "[admin]-字典接口")
@RestController
@RequestMapping("/dict")
public class SysDictController {

    @Resource
    ISysDictTypeService sysDictTypeService;

    @Resource
    ISysDictDataService sysDictDataService;



    @ApiOperation("字典类型列表")
    @Log(title = "字典类型列表")
    @SaAdminCheckPermission("system:dictType:list")
    @PostMapping("/type/list")
    public List<SysDictType> dictTypeList() {

        return sysDictTypeService.list();
    }

    @ApiOperation("保存字典类型")
    @Log(title = "保存字典类型")
    @SaAdminCheckPermission("system:dictType:save")
    @PostMapping("/type")
    public boolean save(@Valid @RequestBody SysDictTypeSaveDTO dto) {
        return sysDictTypeService.saveOrUpdateWithCache(dto);
    }

    @ApiOperation("删除字典类型")
    @Log(title = "删除字典类型")
    @SaAdminCheckPermission("system:dictType:delete")
    @PostMapping("/type/remove")
    public boolean removeType(@RequestBody List<Long> ids) {
        return sysDictTypeService.removeBatchByIdsWithCache(ids);
    }

    @ApiOperation("保存字典数据")
    @Log(title = "保存字典数据")
    @SaAdminCheckPermission("system:dict:save")
    @PostMapping("/data")
    public boolean saveData(@RequestBody SysDictDataDTO dto) {
        return sysDictDataService.saveOrUpdateWithCache(dto);
    }


    @ApiOperation("字典数据分页查询")
    @Log(title = "字典数据分页查询")
    @SaAdminCheckPermission("system:dict:page")
    @PostMapping("/data/page")
    public IPage<SysDictVO> dataPage(@RequestBody SysDictDataFilterDTO dto) {
        return sysDictDataService.dataPage(dto);
    }

    @ApiOperation("删除数据")
    @Log(title = "删除数据")
    @SaAdminCheckPermission("system:dict:delete")
    @PostMapping("/data/remove")
    public boolean removeData(@RequestBody List<Long> ids) {
        return sysDictDataService.removeBatchByIdsWithCache(ids);
    }




    @ApiOperation("获取字典分组及其对应的字典数据项")
    @Log(title = "获取字典分组及其对应的字典数据项")
    @PostMapping("/groups")
    public List<SysDictGroupVO> getDictGroups(@RequestBody(required = false) SysDictGroupDTO dto) {
        log.error("error log print");
        return sysDictTypeService.getDictGroups(dto);
    }

    @ApiOperation("获取字典分组及其对应的字典数据项")
    @Log(title = "获取字典分组及其对应的字典数据项")
    @PostMapping("/testGroups")
    public String testGroups(@RequestBody(required = false) SysDictGroupDTO dto) {
//        return sysDictTypeService.getDictGroups(dto);
        return "ACK";
    }


}
