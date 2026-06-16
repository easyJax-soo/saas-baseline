package com.baseline.system.controller.api;

import com.baseline.common.controller.AdminController;
import com.baseline.system.dto.SysMemberInfoUpdateDTO;
import com.baseline.system.service.ISysMemberService;
import com.baseline.system.vo.SysMemberInfoVO;
import com.baseline.utils.security.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

@Api(tags = "[api]-会员信息")
@RestController
@RequestMapping("/member")
public class SysMemberApiController extends AdminController {

    @Resource
    private ISysMemberService sysMemberService;


    @ApiOperation("获取会员完整信息（包含实名认证信息）")
    @PostMapping("/info")
    public SysMemberInfoVO info() {
        Long memberId = SecurityUtils.getUserId();
        return sysMemberService.getMemberInfo(memberId);
    }

    @ApiOperation("编辑会员信息")
    @PostMapping("/updateInfo")
    public boolean updateInfo(@Valid @RequestBody SysMemberInfoUpdateDTO dto) {
        return sysMemberService.updateMemberInfo(dto);
    }
}
