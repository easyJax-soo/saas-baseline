package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * 租户用户绑定DTO
 *
 * @author system
 */
@Data
@ApiModel(value = "SysTenantUserBindDTO", description = "租户用户绑定DTO")
public class SysTenantUserBindDTO {

    @ApiModelProperty(value = "租户ID", required = true)
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty(value = "是否租户管理员")
    private Boolean isTenantAdmin = false;
}
