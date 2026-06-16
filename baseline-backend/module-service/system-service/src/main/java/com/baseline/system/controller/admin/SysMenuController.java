package com.baseline.system.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysMenuDetailDTO;
import com.baseline.system.dto.SysMenuFilterDTO;
import com.baseline.system.dto.SysMenuQueryDTO;
import com.baseline.system.dto.SysMenuSaveDTO;
import com.baseline.system.entity.SysMenu;
import com.baseline.system.service.ISysMenuService;
import com.baseline.system.vo.SysMenuNodeVO;
import com.baseline.system.vo.SysMenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;


@Api(tags = "[admin]-系统菜单")
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Resource
    ISysMenuService sysMenuService;
    @ApiOperation("保存菜单")
    @Log(title = "系统菜单-保存")
    @SaAdminCheckPermission("system:menu:save")
    @PostMapping("/saveOrUpdate")
    public boolean save(@Valid @RequestBody SysMenuSaveDTO dto) {
        return sysMenuService.saveOrUpdate(dto);
    }

    @ApiOperation("菜单详情")
    @Log(title = "系统菜单-详情")
    @SaAdminCheckPermission("system:menu:detail")
    @PostMapping("/detail")
    public SysMenuVO detail(@RequestBody SysMenuDetailDTO dto) {
        SysMenu entity = sysMenuService.lambdaQuery().eq(SysMenu::getId, dto.getId()).one();
        SysMenuVO vo = BeanUtil.copyProperties(entity, SysMenuVO.class);
        return vo;
    }

    @ApiOperation("删除菜单")
    @Log(title = "系统菜单-删除")
    @SaAdminCheckPermission("system:menu:delete")
    @PostMapping("/remove")
    public boolean remove(@RequestBody List<Long> ids) {
        return sysMenuService.safeRemoveByIds(ids);
    }


    @ApiOperation("菜单树形列表")
    @Log(title = "系统菜单-菜单树形列表")
    @SaAdminCheckPermission("system:menu:tree")
    @PostMapping("/tree")
    public List<SysMenuNodeVO> getNodeList(@RequestBody SysMenuFilterDTO dto) {
        return sysMenuService.getNodeList(dto);
    }


    @ApiOperation("菜单按钮列表")
    @Log(title = "系统菜单-菜单按钮列表")
    @SaAdminCheckPermission("system:menuBtn:list")
    @GetMapping("/btn/list/{menuId}")
    public List<SysMenuVO> getBtnNodeList(@PathVariable("menuId") Long id) {
        return sysMenuService.getBtnNodeList(id);
    }



    @ApiOperation("我的菜单列表")
    @Log(title = "系统菜单-我的菜单列表")
    @PostMapping("/list")
    public List<SysMenuNodeVO> getMyNodeList(@RequestBody(required = false) SysMenuQueryDTO dto) {
        return sysMenuService.getMyNodeList(dto);
    }

    @ApiOperation("我的按钮权限")
    @Log(title = "系统菜单-我的按钮权限")
    @PostMapping("/btn/permission")
    public List<String> getMyBtnPermission(@RequestBody(required = false) SysMenuQueryDTO dto) {
        return sysMenuService.getMyBtnPermission(dto);
    }



}
