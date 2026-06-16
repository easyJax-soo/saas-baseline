package com.baseline.system.controller.admin;

import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysDeptDetailDTO;
import com.baseline.system.dto.SysDeptFilterDTO;
import com.baseline.system.dto.SysDeptSaveDTO;
import com.baseline.system.entity.SysDept;
import com.baseline.system.service.ISysDeptService;
import com.baseline.system.vo.SysDeptNodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;


@Api(tags = "[admin]-部门信息")
@RestController
@RequestMapping("/dept")
public class SysDeptController {

    @Resource
    ISysDeptService sysDeptService;


    @ApiOperation("保存部门")
    @Log(title = "部门信息-保存部门")
    @SaAdminCheckPermission("system:dept:save")
    @PostMapping("/saveOrUpdate")
    public boolean save(@Valid @RequestBody SysDeptSaveDTO dto) {
        return sysDeptService.saveOrUpdate(dto);
    }

    @ApiOperation("部门详情")
    @Log(title = "部门信息-部门详情")
    @SaAdminCheckPermission("system:dept:detail")
    @PostMapping("/detail")
    public SysDept detail(@RequestBody SysDeptDetailDTO dto) {
        return sysDeptService.lambdaQuery().eq(SysDept::getId, dto.getId()).one();
    }

    @ApiOperation("删除部门")
    @Log(title = "部门信息-删除部门")
    @SaAdminCheckPermission("system:dept:delete")
    @PostMapping("/remove")
    public boolean remove(@RequestBody List<Long> ids) {
        return sysDeptService.safeRemoveByIds(ids);
    }

    @ApiOperation("部门树形列表")
    @Log(title = "部门信息-树形列表")
    @SaAdminCheckPermission("system:dept:tree")
    @PostMapping("/list")
    public List<SysDeptNodeVO> getNodeList(@RequestBody SysDeptFilterDTO dto) {
        return sysDeptService.getNodeList(dto);
    }




}
