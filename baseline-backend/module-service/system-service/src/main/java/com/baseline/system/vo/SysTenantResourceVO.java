package com.baseline.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 租户资源VO（包含权限、菜单、项目）
 *
 * @author system
 */
@Data
@ApiModel(value = "SysTenantResourceVO", description = "租户资源VO")
public class SysTenantResourceVO {

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty(value = "租户权限ID列表")
    private List<Long> permissionIds;

    @ApiModelProperty(value = "租户菜单ID列表")
    private List<Long> menuIds;

    @ApiModelProperty(value = "租户项目编码列表")
    private List<String> projectCodes;
}
