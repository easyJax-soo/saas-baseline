package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@ApiModel(value = "SysMemberResetPasswordDTO", description = "重置会员密码")
public class SysMemberResetPasswordDTO {

    @ApiModelProperty(value = "会员ID", required = true)
    @NotNull(message = "会员ID不能为空")
    private Long memberId;

    @ApiModelProperty(value = "新密码", required = true)
    @NotNull(message = "新密码不能为空")
    private String newPassword;
}
