package com.baseline.system.dto;

import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@ApiModel(value = "SysMemberRealNameAuthAuditDTO", description = "会员实名认证审核DTO")
public class SysMemberRealNameAuthAuditDTO {

    @ApiModelProperty(value = "认证ID", required = true)
    @NotNull(message = "认证ID不能为空")
    private Long id;

    @ApiModelProperty(value = "审核状态：1-审核通过，2-审核拒绝", required = true)
    @NotNull(message = "审核状态不能为空")
    @DictVaild(dictType = "sysAuthStatus")
    private Integer authStatus;

    @ApiModelProperty(value = "审核备注")
    @Size(max = 500, message = "审核备注长度不能超过500个字符")
    private String auditRemark;
}
