package com.baseline.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(value = "SysMemberFilterBizDTO", description = "会员查询过滤业务条件")
public class SysMemberFilterBizDTO {

    @ApiModelProperty(value = "会员昵称")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "开始日期")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束日期")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "id集合", hidden = true)
    private List<Long> ids;

    @ApiModelProperty(value = "会员状态")
    private Integer status;
}
