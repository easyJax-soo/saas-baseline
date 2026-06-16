package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysTenantDetailDTO", description = "系统租户详情查询参数")
public class SysTenantDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户ID")
    private Long id;

    @ApiModelProperty(value = "租户编码")
    private String code;

}
