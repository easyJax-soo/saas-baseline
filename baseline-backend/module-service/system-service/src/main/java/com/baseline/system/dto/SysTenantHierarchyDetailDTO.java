package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * <p>
 * 系统租户层级详情查询DTO
 * </p>
 *
 * @author system
 * @since 2025-11-18
 */
@Data
@ApiModel(value = "SysTenantHierarchyDetailDTO对象", description = "系统租户层级详情查询DTO")
public class SysTenantHierarchyDetailDTO {

    @ApiModelProperty(value = "租户ID", required = true)
    @NotNull(message = "租户ID不能为空")
    private Long id;

}
