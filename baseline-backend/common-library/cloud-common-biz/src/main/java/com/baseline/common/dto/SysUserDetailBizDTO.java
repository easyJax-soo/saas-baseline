package com.baseline.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysUserDetailBizDTO", description = "用户详情业务查询条件")
public class SysUserDetailBizDTO {

    @ApiModelProperty(value = "用户ID")
    private Long id;
}
