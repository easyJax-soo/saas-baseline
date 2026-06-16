package com.baseline.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 租户详情查询DTO
 *
 * @author cascade
 * @date 2025/11/17
 */
@ApiModel(value = "SysTenantDetailBizDTO对象", description = "租户详情查询条件")
@Data
public class SysTenantDetailBizDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户ID")
    private Long id;

    @ApiModelProperty(value = "租户编码")
    private String code;
}
