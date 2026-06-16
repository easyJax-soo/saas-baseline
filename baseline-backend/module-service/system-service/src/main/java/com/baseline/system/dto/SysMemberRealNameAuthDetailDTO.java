package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@ApiModel(value = "SysMemberRealNameAuthDetailDTO", description = "会员实名认证详情查询DTO")
public class SysMemberRealNameAuthDetailDTO {

    @ApiModelProperty(value = "会员ID", required = true)
    @NotNull(message = "会员ID不能为空")
    private Long memberId;
}
