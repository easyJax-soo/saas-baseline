package com.baseline.system.controller.api;


import com.baseline.log.annotation.Log;
import com.baseline.system.entity.SysMessageInfo;
import com.baseline.system.service.ISysMessageInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;


/**
 * 消息中心 控制器
 **/
@Api(tags = "[api]-消息中心")
@RestController
@RequestMapping("/sysMessageInfo")
public class SysMessageInfoApiController {

    @Resource
    ISysMessageInfoService sysMessageInfoService;


    @ApiOperation("获取个人最新系统通知")
    @PostMapping("/newNotice")
    public SysMessageInfo newNotice() {
        return sysMessageInfoService.getNewSysNotice();
    }

    @ApiOperation("获取个人系统通知列表")
    @PostMapping("/listNotice")
    public List<SysMessageInfo> listNotice() {
        return sysMessageInfoService.listNotice();
    }

    @ApiOperation("全部已读")
    @Log(title = "消息中心-全部已读")
    @GetMapping("/allRead")
    public boolean allRead() {
        return sysMessageInfoService.allRead();
    }
}
