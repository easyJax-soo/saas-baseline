package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysUserChangePasswordDTO", description = "重置密码")
public class SysUserChangePasswordDTO {

    @ApiModelProperty(value = "旧密码")
    private String oldPw;

    @ApiModelProperty(value = "新密码")
    private String newPw;
}
