package com.baseline.system.controller.feign;


import com.baseline.system.dto.SysMessageInfoSaveTypeDTO;
import com.baseline.system.service.ISysMessageInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;


/**
 * 消息中心 控制器
 **/
@Api(tags = "[Feign]-消息中心")
@RestController
@RequestMapping("/sysMessageInfo")
public class SysMessageInfoFeignController {

    @Resource
    ISysMessageInfoService sysMessageInfoService;

    @ApiOperation("按类型添加消息")
    @PostMapping("/saveByType")
    public boolean saveByType(@RequestBody SysMessageInfoSaveTypeDTO dto) {
        return sysMessageInfoService.saveByType(dto);
    }

}
