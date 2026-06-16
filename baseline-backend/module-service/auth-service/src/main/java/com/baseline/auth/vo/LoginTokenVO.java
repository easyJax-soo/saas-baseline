package com.baseline.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "LoginTokenVO", description = "获取token")
@Data
public class LoginTokenVO {

    @ApiModelProperty(value = "Token")
    private String token;
    @ApiModelProperty(value = "token前缀")
    private String tokenPrefix;
    @ApiModelProperty(value = "过期时间")
    private Long expired;
    @ApiModelProperty(value = "登录类型")
    private String loginType;

//    @ApiModelProperty(value = "角色列表")
//    private List<String> roles;

//    @ApiModelProperty(value = "权限列表")
//    private List<String> permissions;

//    @ApiModelProperty(value = "允许访问的服务")
//    private List<String> services;
}
