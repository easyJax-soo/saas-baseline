package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 租户资源保存DTO（包含权限、菜单、项目）
 *
 * @author system
 */
@Data
@ApiModel(value = "SysTenantResourceSaveDTO", description = "租户资源保存DTO")
public class SysTenantResourceSaveDTO {

    @ApiModelProperty(value = "租户ID", required = true)
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;

    @ApiModelProperty(value = "租户权限ID列表")
    private List<Long> permissionIds;

    @ApiModelProperty(value = "租户菜单ID列表")
    private List<Long> menuIds;

    @ApiModelProperty(value = "租户项目编码列表")
    private List<String> projectCodes;
}
