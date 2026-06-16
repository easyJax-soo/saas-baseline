package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 系统租户树查询DTO
 * </p>
 *
 * @author system
 * @since 2025-11-18
 */
@Data
@ApiModel(value = "SysTenantTreeDTO对象", description = "系统租户树查询DTO")
public class SysTenantTreeDTO {

    @ApiModelProperty(value = "父级租户ID")
    private Long parentId;

}
