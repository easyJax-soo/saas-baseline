package com.baseline.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 租户查询过滤DTO
 *
 * @author cascade
 * @date 2025/11/17
 */
@ApiModel(value = "SysTenantFilterBizDTO对象", description = "租户查询过滤条件")
@Data
public class SysTenantFilterBizDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户ID")
    private Long id;

    @ApiModelProperty(value = "租户名称")
    private String name;

    @ApiModelProperty(value = "租户编码")
    private String code;

    @ApiModelProperty(value = "父租户ID")
    private Long parentId;

    @ApiModelProperty(value = "帐号状态")
    private Integer status;

    @ApiModelProperty(value = "是否包含子租户")
    private Boolean includeChildren;
}
