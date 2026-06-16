package com.baseline.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysDeptFilterBizDTO",description = "部门查询过滤业务条件")
public class SysDeptFilterBizDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门 ID")
    Long id;

    @ApiModelProperty(value = "部门名称")
    String name;

    @ApiModelProperty(value = "部门编码")
    String code;

}
