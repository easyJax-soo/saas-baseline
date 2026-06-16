package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 租户用户解绑DTO
 *
 * @author system
 */
@Data
@ApiModel(value = "SysTenantUserUnbindDTO", description = "租户用户解绑DTO")
public class SysTenantUserUnbindDTO {

    @ApiModelProperty(value = "租户ID", required = true)
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;

    @ApiModelProperty(value = "用户ID列表", required = true)
    @NotEmpty(message = "用户ID列表不能为空")
    private List<Long> userIds;
}
