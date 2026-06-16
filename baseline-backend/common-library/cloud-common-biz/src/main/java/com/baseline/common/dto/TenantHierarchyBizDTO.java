package com.baseline.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 租户层级查询业务DTO
 *
 * @author cascade
 * @date 2025/11/17
 */
@Data
@ApiModel(value = "TenantHierarchyBizDTO", description = "租户层级查询业务条件")
public class TenantHierarchyBizDTO {

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;
}
