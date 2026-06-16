package com.baseline.auth.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 用户登录对象
 * 
 * @author ruoyi
 */
@Data
public class LoginBashDTO<T>
{
    @ApiModelProperty(value = "登录类型，比如管理后台登录 admin")
    @NotBlank(message = "登录类型不能为空")
    private String loginType;

    @ApiModelProperty(value = "认证类型，比如账号密码登录")
    @NotBlank(message = "认证类型不能未空")
    private String authType;

    private T credentials; // 泛型认证参数

}
