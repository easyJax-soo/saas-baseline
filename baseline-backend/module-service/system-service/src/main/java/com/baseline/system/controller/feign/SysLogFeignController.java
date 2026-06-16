package com.baseline.system.controller.feign;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.SysOperLogBizDTO;
import com.baseline.system.service.ISysOplogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * [Feign]-系统日志服务接口
 *
 * @author system
 */
@Api(tags = "[Feign]-系统日志接口")
@RestController
@RequestMapping("/log")
public class SysLogFeignController {

    @Resource
    private ISysOplogService sysOplogService;

    @ApiOperation("[Feign]-保存系统操作日志")
    @PostMapping("/saveOperLog")
    public boolean saveOperLog(@RequestBody SysOperLogBizDTO sysOperLogBizDTO, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        return sysOplogService.saveOperLog(sysOperLogBizDTO);
    }
}
