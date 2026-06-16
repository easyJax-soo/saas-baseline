package com.baseline.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "UserTenantVO", description = "用户可访问租户VO")
public class UserTenantVO {

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty(value = "租户编码")
    private String tenantCode;

    @ApiModelProperty(value = "是否为当前租户")
    private Boolean isCurrent;

    @ApiModelProperty(value = "是否为租户管理员")
    private Boolean isTenantAdmin;

    @ApiModelProperty(value = "租户状态")
    private Integer tenantStatus;
}
