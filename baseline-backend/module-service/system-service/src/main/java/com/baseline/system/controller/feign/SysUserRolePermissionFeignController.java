package com.baseline.system.controller.feign;


import com.baseline.common.dto.UserPermissionBizDTO;
import com.baseline.common.dto.UserRoleBizDTO;
import com.baseline.common.vo.UserPermissionBizVO;
import com.baseline.common.vo.UserRoleBizVO;
import com.baseline.system.service.ISysPermissionService;
import com.baseline.system.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * [Feign]-用户角色权限服务接口
 *
 * @author system
 */
@Api(tags = "[Feign]-用户角色权限接口")
@RestController
@RequestMapping("/permission")
public class SysUserRolePermissionFeignController {

    @Autowired
    ISysPermissionService sysPermissionService;

    @ApiOperation("[Feign]-根据用户ID和角色ID获取权限列表")
    @PostMapping("/userRolePermission")
    public List<UserPermissionBizVO> getPermissionsByUserIdAndRoleId(@Valid @RequestBody UserPermissionBizDTO dto){
        return sysPermissionService.getPermissionsByUserIdAndRoleId(dto);
    }


}
