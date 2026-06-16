package com.baseline.system.controller.admin;


import com.baseline.common.constant.MessageInfoConstants;
import com.baseline.log.annotation.Log;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baseline.system.dto.OperateIdDTO;
import com.baseline.system.vo.SysMessageTypeNumVO;
import com.baseline.utils.security.SecurityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import jakarta.annotation.Resource;
import java.util.List;

import com.baseline.system.service.ISysMessageInfoService;
import com.baseline.system.dto.SysMessageInfoFilterDTO;
import  com.baseline.system.vo.SysMessageInfoVO;


/**
 * 消息中心 控制器
 **/
@Api(tags = "[admin]-消息中心")
@RestController
@RequestMapping("/sysMessageInfo")
public class SysMessageInfoController {

    @Resource
    ISysMessageInfoService sysMessageInfoService;


    @ApiOperation("分页数据")
    @Log(title = "消息中心-分页数据")
//    @SaAdminCheckPermission("system:sysMessageInfo:page")
    @PostMapping("/page/{index}/{size}")
    public IPage<SysMessageInfoVO> page(@PathVariable("index") Long index,
                                         @PathVariable("size") Long size,
                                         @RequestBody SysMessageInfoFilterDTO dto) {
        dto.setUserType(MessageInfoConstants.UserType.ADMIN);
        dto.setUserId(SecurityUtils.getUserId());
        return sysMessageInfoService.paging(new Page<>(index,size),dto);
    }

    @ApiOperation("消息数量总览")
    @Log(title = "消息中心-消息数量总览")
//    @SaAdminCheckPermission("system:sysMessageInfo:numOverview")
    @GetMapping("/numOverview")
    public List<SysMessageTypeNumVO> numOverview() {
        return sysMessageInfoService.numOverview();
    }

    @ApiOperation("全部已读")
    @Log(title = "消息中心-全部已读")
//    @SaAdminCheckPermission("system:sysMessageInfo:allRead")
    @GetMapping("/allRead")
    public boolean allRead() {
        return sysMessageInfoService.allRead();
    }

    @ApiOperation("已读")
    @Log(title = "消息中心-已读")
//    @SaAdminCheckPermission("system:sysMessageInfo:allRead")
    @PostMapping("/haveRead")
    public boolean haveRead(@Validated @RequestBody OperateIdDTO dto) {
        return sysMessageInfoService.haveRead(dto.getId());
    }

    @ApiOperation("未读数量")
    @Log(title = "消息中心-未读数量")
//    @SaAdminCheckPermission("system:sysMessageInfo:notReadNum")
    @GetMapping("/notReadNum")
    public Long notReadNum() {
        return sysMessageInfoService.notReadNum();
    }


    @ApiOperation("删除")
    @Log(title = "消息中心-删除")
//    @SaAdminCheckPermission(":sysMessageInfo:delete")
    @PostMapping("/remove")
    public boolean remove(@RequestBody List<Long> ids) {
        return sysMessageInfoService.removeBatchByIds(ids);
    }

}
