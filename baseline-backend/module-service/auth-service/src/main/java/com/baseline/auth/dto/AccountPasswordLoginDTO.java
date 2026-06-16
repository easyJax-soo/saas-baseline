package com.baseline.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 账号密码登录
 * 
 * @author ruoyi
 */
@ApiModel(value = "AccountPasswordLoginDTO", description = "账号密码登录")
@Data
public class AccountPasswordLoginDTO
{

    @ApiModelProperty(value = "用户账号")
    @NotBlank(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "用户密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
