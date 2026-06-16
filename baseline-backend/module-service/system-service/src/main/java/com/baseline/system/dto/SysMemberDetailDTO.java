package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@ApiModel(value = "SysMemberDetailDTO", description = "会员详情查询DTO")
public class SysMemberDetailDTO {

    @ApiModelProperty(value = "会员ID", required = true)
    @NotNull(message = "会员ID不能为空")
    private Long id;
}
