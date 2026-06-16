package com.baseline.system.controller.feign;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.system.service.ISysProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * [Feign]-系统项目服务接口
 *
 * @author system
 */
@Api(tags = "[Feign]-系统项目接口")
@RestController
@RequestMapping("/project")
public class SysProjectFeignController {

    @Resource
    private ISysProjectService sysProjectService;

    @ApiOperation("[Feign]-获取当前用户有权限访问的项目编码列表")
    @PostMapping("/getUserProjectCodes")
    public List<String> getUserProjectCodes(@RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        return sysProjectService.getUserProjectCodes();
    }
}
