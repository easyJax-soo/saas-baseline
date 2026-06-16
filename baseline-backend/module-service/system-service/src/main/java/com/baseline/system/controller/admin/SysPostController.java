package com.baseline.system.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysPostDetailDTO;
import com.baseline.system.dto.SysPostFilterDTO;
import com.baseline.system.dto.SysPostSaveDTO;
import com.baseline.system.service.ISysPostService;
import com.baseline.system.vo.SysPostVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

@Api(tags = "[admin]-岗位")
@RestController
@RequestMapping("/post")
public class SysPostController {

    @Resource
    private ISysPostService sysPostService;

    @ApiOperation("岗位分页")
    @Log(title = "岗位-分页")
    @SaAdminCheckPermission("system:post:page")
    @PostMapping("/page")
    public IPage<SysPostVO> page(@RequestBody SysPostFilterDTO dto) {
        return sysPostService.pagePost(dto);
    }

    @ApiOperation("新增编辑岗位")
    @Log(title = "岗位-保存")
    @SaAdminCheckPermission("system:post:save")
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@Valid @RequestBody SysPostSaveDTO dto) {
        return sysPostService.saveOrUpdate(dto);
    }

    @ApiOperation("岗位详情")
    @Log(title = "岗位-详情")
    @SaAdminCheckPermission("system:post:detail")
    @PostMapping("/detail")
    public SysPostVO detail(@RequestBody SysPostDetailDTO dto) {
        return sysPostService.detail(dto);
    }


    @ApiOperation("批量删除岗位")
    @Log(title = "岗位-删除")
    @SaAdminCheckPermission("system:post:delete")
    @PostMapping("/remove")
    public boolean remove(@RequestBody List<Long> ids) {
        return sysPostService.remove(ids);
    }
}
