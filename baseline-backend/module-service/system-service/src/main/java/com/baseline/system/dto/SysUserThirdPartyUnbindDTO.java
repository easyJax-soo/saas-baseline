package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户第三方解绑DTO
 * 
 * @author system
 */
@ApiModel("用户第三方解绑")
@Data
public class SysUserThirdPartyUnbindDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "第三方标识", required = true)
    @NotBlank(message = "第三方标识不能为空")
    private String provider;

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户ID不能为空")
    private Long userId;
}
