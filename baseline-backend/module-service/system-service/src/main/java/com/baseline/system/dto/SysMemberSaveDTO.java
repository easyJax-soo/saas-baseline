package com.baseline.system.dto;

import com.baseline.common.annotation.Dict;
import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@ApiModel(value = "SysMemberSaveDTO", description = "会员保存DTO")
public class SysMemberSaveDTO {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "登录账号", required = true)
    @NotBlank(message = "登录账号不能为空")
    @Size(min = 3, max = 30, message = "账号长度必须在3-30个字符之间")
    private String account;

    @ApiModelProperty(value = "密码")
    @Size(min = 8, message = "密码长度至少为8个字符")
    private String password;

    @ApiModelProperty(value = "用户昵称", required = true)
    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String name;

    @ApiModelProperty(value = "用户邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    @ApiModelProperty(value = "手机号码")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码格式不正确")
    private String phone;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    @Dict(dictType = "sysSex")
    @DictVaild(dictType = "sysSex")
    private Integer sex;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "帐号状态")
    @NotNull(message = "帐号状态不能为空")
    @DictVaild(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "备注")
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
}
