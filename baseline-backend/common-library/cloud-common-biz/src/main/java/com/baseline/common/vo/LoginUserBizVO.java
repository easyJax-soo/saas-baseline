package com.baseline.common.vo;

import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginUserBizVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户 ID")
    private Long id;

    @ApiModelProperty("用户类型")
    private String LoginType;

    @ApiModelProperty("用户 账号")
    private String username;

    @ApiModelProperty("用户密码")
//    @JsonIgnore
    private String password;

    @ApiModelProperty(value = "盐加密")
    private String salt;

    @ApiModelProperty("用户状态")
    @Dict(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty("租户ID")
    private Long tenantId;

    @ApiModelProperty("用户角色ID列表")
    private List<Long> roleIds;

    @ApiModelProperty("用户角色标识列表")
    private List<String> roles;

    @ApiModelProperty("用户菜单列表")
    private List<String> menus;

    @ApiModelProperty("用户权限列表")
    private List<String> permissions;

    @ApiModelProperty("用户部门ID列表")
    private List<Long> deptIds;

    @ApiModelProperty("用户部门标识列表")
    private List<String> deptCodes;
}
