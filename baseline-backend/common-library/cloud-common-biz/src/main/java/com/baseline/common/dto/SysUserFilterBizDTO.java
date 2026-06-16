package com.baseline.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(value = "SysUserFilterBizDTO",description = "用户查询过滤业务条件")
public class SysUserFilterBizDTO {

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "开始日期 ")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束日期 ")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "部门 ID")
    private Long deptId;

    @ApiModelProperty(value = "角色 ID")
    private Long roleId;

    @ApiModelProperty(value = "id集合", hidden = true)
    private List<Long> ids;
}
