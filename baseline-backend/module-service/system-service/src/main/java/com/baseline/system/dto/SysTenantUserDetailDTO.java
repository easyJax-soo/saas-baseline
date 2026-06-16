package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysTenantUserDetailDTO", description = "系统租户用户详情查询参数")
public class SysTenantUserDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户用户ID", required = true)
    private Long id;

}
