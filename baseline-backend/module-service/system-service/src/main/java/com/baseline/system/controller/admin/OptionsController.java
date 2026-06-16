package com.baseline.system.controller.admin;

import com.baseline.common.dto.OptionsQueryBizDTO;
import com.baseline.system.service.impl.OptionsServiceImpl;
import com.baseline.common.security.annotation.SaAdminCheckPermission;
import com.baseline.common.vo.TreeOptionVO;
import com.baseline.log.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 下拉选项控制器
 * 
 * @author system
 */
@Api(tags = "[admin]-下拉选项管理")
@RestController
@RequestMapping("/options")
public class OptionsController {

    @Autowired
    private OptionsServiceImpl optionsService;

    @ApiOperation("获取用户选项列表")
    @Log(title = "下拉选项-用户列表")
    @SaAdminCheckPermission("system:options:user")
    @PostMapping("/users")
    public List<TreeOptionVO> getUserOptions(@RequestBody(required = false) OptionsQueryBizDTO queryDTO) {
        return optionsService.getUserOptions(queryDTO);
    }

    @ApiOperation("获取角色选项列表")
    @Log(title = "下拉选项-角色列表")
    @SaAdminCheckPermission("system:options:role")
    @PostMapping("/roles")
    public List<TreeOptionVO> getRoleOptions(@RequestBody(required = false) OptionsQueryBizDTO queryDTO) {
        return optionsService.getRoleOptions(queryDTO);
    }

    @ApiOperation("获取部门选项树")
    @Log(title = "下拉选项-部门树")
    @SaAdminCheckPermission("system:options:dept")
    @PostMapping("/depts")
    public List<TreeOptionVO> getDeptOptions(@RequestBody(required = false) OptionsQueryBizDTO queryDTO) {
        return optionsService.getDeptOptions(queryDTO);
    }

    @ApiOperation("获取菜单选项树")
    @Log(title = "下拉选项-菜单树")
    @SaAdminCheckPermission("system:options:menu")
    @PostMapping("/menus")
    public List<TreeOptionVO> getMenuOptions(@RequestBody(required = false) OptionsQueryBizDTO queryDTO) {
        return optionsService.getMenuOptions(queryDTO);
    }

    @ApiOperation("获取权限选项树")
    @Log(title = "下拉选项-权限树")
    @SaAdminCheckPermission("system:options:permission")
    @PostMapping("/permissions")
    public List<TreeOptionVO> getPermissionOptions(@RequestBody(required = false) OptionsQueryBizDTO queryDTO) {
        return optionsService.getPermissionOptions(queryDTO);
    }

    @ApiOperation("获取岗位选项列表")
    @Log(title = "下拉选项-岗位列表")
    @SaAdminCheckPermission("system:options:post")
    @PostMapping("/posts")
    public List<TreeOptionVO> getPostOptions(@RequestBody(required = false) OptionsQueryBizDTO queryDTO) {
        return optionsService.getPostOptions(queryDTO);
    }

    @ApiOperation("获取项目选项列表")
    @Log(title = "下拉选项-项目列表")
    @SaAdminCheckPermission("system:options:project")
    @PostMapping("/projects")
    public List<TreeOptionVO> getProjectOptions(@RequestBody(required = false) OptionsQueryBizDTO queryDTO) {
        return optionsService.getProjectOptions(queryDTO);
    }

}
