package com.baseline.system.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysProjectDetailDTO;
import com.baseline.system.dto.SysProjectFilterDTO;
import com.baseline.system.dto.SysProjectSaveDTO;
import com.baseline.system.service.ISysProjectService;
import com.baseline.system.vo.SysProjectPageVO;
import com.baseline.system.vo.SysProjectVO;
import com.baseline.system.vo.SysProjectTypeGroupVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>
 * 系统项目管理 控制器
 * </p>
 *
 * @author baseline
 * @since 2024-01-01
 */
@Api(tags = "[admin]-系统项目管理")
@RestController
@RequestMapping("/project")
public class SysProjectController {

    @Resource
    private ISysProjectService sysProjectService;

    @ApiOperation("保存项目")
    @Log(title = "系统项目-保存")
    @SaAdminCheckPermission("system:project:save")
    @PostMapping("/saveOrUpdate")
    public boolean save(@Valid @RequestBody SysProjectSaveDTO dto) {
        return sysProjectService.saveOrUpdate(dto);
    }

    @ApiOperation("项目详情")
    @Log(title = "系统项目-详情")
    @SaAdminCheckPermission("system:project:detail")
    @PostMapping("/detail")
    public SysProjectVO detail(@RequestBody SysProjectDetailDTO dto) {
        return sysProjectService.getDetail(dto.getId());
    }

    @ApiOperation("删除项目")
    @Log(title = "系统项目-删除")
    @SaAdminCheckPermission("system:project:delete")
    @PostMapping("/remove")
    public boolean remove(@RequestBody List<Long> ids) {
        return sysProjectService.removeByIds(ids);
    }

    @ApiOperation("项目分页列表")
    @Log(title = "系统项目-分页列表")
    @SaAdminCheckPermission("system:project:page")
    @PostMapping("/page")
    public IPage<SysProjectPageVO> pageProject(@RequestBody SysProjectFilterDTO dto) {
        return sysProjectService.pageProject(dto);
    }

    @ApiOperation("按项目类型分类获取项目列表")
    @Log(title = "系统项目-按类型分类列表")
    @PostMapping("/listByType")
    public List<SysProjectTypeGroupVO> getProjectsByType() {
        return sysProjectService.getProjectsByType();
    }
}
