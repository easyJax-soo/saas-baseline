package com.baseline.system.dto;

import com.baseline.common.dto.base.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 租户未绑定用户查询DTO
 *
 * @author system
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysTenantUnboundUserFilterDTO", description = "租户未绑定用户查询DTO")
public class SysTenantUnboundUserFilterDTO extends PageDTO implements Serializable {

    @ApiModelProperty(value = "租户ID", required = true)
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;

    @ApiModelProperty(value = "用户账号（模糊查询）")
    private String account;

    @ApiModelProperty(value = "用户姓名（模糊查询）")
    private String name;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;
}
