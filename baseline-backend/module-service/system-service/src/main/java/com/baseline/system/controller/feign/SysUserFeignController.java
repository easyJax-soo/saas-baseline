package com.baseline.system.controller.feign;


import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.dto.SysUserDetailBizDTO;
import com.baseline.common.dto.SysUserFilterBizDTO;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.common.vo.SysUserVO;
import com.baseline.system.dto.SysUserDetailDTO;
import com.baseline.system.dto.SysUserFilterDTO;
import com.baseline.system.service.ISysUserService;
import com.baseline.system.vo.SysUserDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * [Feign]-用户服务接口
 *
 * @author cascade
 * @date 2025/11/17
 */
@Api(tags = "[Feign]-用户接口")
@RestController
@RequestMapping("/user")
public class SysUserFeignController {

    @Autowired
    ISysUserService sysUserService;

    @ApiOperation("[Feign]-根据用户名获取用户信息")
    @PostMapping("/getUserByUsername")
    public LoginUserBizVO getUserByUsername(@Valid @RequestBody LoginUserBizDTO dto){
        return sysUserService.getLoginUserByUsername(dto);
    }

    @ApiOperation("[Feign]-获取用户详情")
    @PostMapping("/detail")
    public SysUserVO detail(@RequestBody SysUserDetailBizDTO dto) {
        // 转换为 system 包的 SysUserDetailDTO
        SysUserDetailDTO systemDto = new SysUserDetailDTO();
        BeanUtils.copyProperties(dto, systemDto);
        
        // 调用现有的 detail 方法
        SysUserDetailVO detailVO = sysUserService.detail(systemDto);
        
        // 转换为 common 包的 SysUserVO
        SysUserVO userVO = new SysUserVO();
        if (detailVO != null) {
            BeanUtils.copyProperties(detailVO, userVO);
        }
        return userVO;
    }

    @ApiOperation("[Feign]-获取用户简单列表")
    @PostMapping("/simpleList")
    public List<SysUserVO> simpleList(@RequestBody SysUserFilterBizDTO dto) {
        // 转换 DTO
        SysUserFilterDTO systemDto = new SysUserFilterDTO();
        BeanUtils.copyProperties(dto, systemDto);
        
        // 调用服务方法并转换结果
        List<com.baseline.system.vo.SysUserVO> systemUserList = sysUserService.getSimpleList(systemDto);
        
        // 转换为 common 包的 SysUserVO
        return systemUserList.stream().map(systemUser -> {
            SysUserVO userVO = new SysUserVO();
            BeanUtils.copyProperties(systemUser, userVO);
            return userVO;
        }).collect(java.util.stream.Collectors.toList());
    }
}
