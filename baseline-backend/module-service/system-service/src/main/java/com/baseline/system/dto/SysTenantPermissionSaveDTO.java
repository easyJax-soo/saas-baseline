package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "SysTenantPermissionSaveDTO",description = "租户权限保存信息")
@Data
public class SysTenantPermissionSaveDTO implements Serializable {


    @ApiModelProperty(value = "租户ID")
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;

    @ApiModelProperty(value = "权限ID")
    @NotEmpty(message = "权限ID不能为空")
    private List<Long> permissionIds;

}
