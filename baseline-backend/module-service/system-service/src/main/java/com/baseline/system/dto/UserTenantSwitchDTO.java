package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@ApiModel(value = "UserTenantSwitchDTO", description = "用户租户切换DTO")
public class UserTenantSwitchDTO {

    @ApiModelProperty(value = "目标租户ID", required = true)
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;
}
