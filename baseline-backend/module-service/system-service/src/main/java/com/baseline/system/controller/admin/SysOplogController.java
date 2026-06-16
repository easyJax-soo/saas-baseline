package com.baseline.system.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysOplogDetailDTO;
import com.baseline.system.dto.SysOplogFilterDTO;
import com.baseline.system.service.ISysOplogService;
import com.baseline.system.vo.SysOplogDetailVO;
import com.baseline.system.vo.SysOplogPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "[admin]-操作日志")
@RestController
@RequestMapping("/oplog")
public class SysOplogController {

    @Autowired
    private ISysOplogService sysOplogService;

    @ApiOperation("操作日志分页列表")
    @Log(title = "操作日志-分页列表")
    @SaAdminCheckPermission("system:oplog:page")
    @PostMapping("/page")
    public IPage<SysOplogPageVO> page(@RequestBody SysOplogFilterDTO dto) {
        return sysOplogService.pageOplog(dto);
    }

    @ApiOperation("操作日志详情")
    @Log(title = "操作日志-详情")
    @SaAdminCheckPermission("system:oplog:detail")
    @PostMapping("/detail")
    public SysOplogDetailVO detail(@RequestBody SysOplogDetailDTO dto) {
        return sysOplogService.getOplogDetail(dto.getId());
    }

    @ApiOperation("批量删除操作日志")
    @Log(title = "操作日志-删除")
    @SaAdminCheckPermission("system:oplog:delete")
    @PostMapping("/remove")
    public boolean remove(@RequestBody List<Long> ids) {
        return sysOplogService.removeBatchByIds(ids);
    }

}
