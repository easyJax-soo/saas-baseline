package com.baseline.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysMemberDetailBizDTO", description = "会员详情业务查询条件")
public class SysMemberDetailBizDTO {

    @ApiModelProperty(value = "会员ID")
    private Long id;
}
