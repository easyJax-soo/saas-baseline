package com.baseline.system.controller.api;

import com.baseline.log.annotation.Log;
import com.baseline.system.dto.SysTenantTreeDTO;
import com.baseline.system.service.ISysTenantService;
import com.baseline.system.vo.SysTenantTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

@Api(tags = "[api]-系统租户")
@RestController
@RequestMapping("/tenant")
public class SysTenantApiController {

    @Resource
    private ISysTenantService sysTenantService;


    @ApiOperation("获取租户树形结构")
    @Log(title = "系统租户-树形结构")
    @PostMapping("/tree")
    public List<SysTenantTreeVO> getTenantTree(@RequestBody SysTenantTreeDTO dto) {
        return sysTenantService.getTenantTree(dto.getParentId());
    }
}
