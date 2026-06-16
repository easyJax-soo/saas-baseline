package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * 租户资源DTO（包含权限、菜单、项目）
 *
 * @author system
 */
@Data
@ApiModel(value = "SysTenantResourceDTO", description = "租户资源DTO")
public class SysTenantResourceDTO {

    @ApiModelProperty(value = "租户ID", required = true)
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;
}
