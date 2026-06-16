package com.baseline.system.controller.feign;


import com.baseline.common.dto.UserRoleBizDTO;
import com.baseline.common.vo.UserRoleBizVO;
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
 * [Feign]-用户角色服务接口
 *
 * @author system
 */
@Api(tags = "[Feign]-用户角色接口")
@RestController
@RequestMapping("/role")
public class SysUserRoleFeignController {

    @Autowired
    ISysRoleService sysRoleService;

    @ApiOperation("[Feign]-根据用户ID获取角色列表")
    @PostMapping("/userRole")
    public List<UserRoleBizVO> getRolesByUserId(@Valid @RequestBody UserRoleBizDTO dto){
        return sysRoleService.getRolesByUserId(dto);
    }


}
