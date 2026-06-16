package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户实名认证审核DTO
 *
 * @author system
 */
@ApiModel("用户实名认证审核")
@Data
public class SysUserRealNameAuthAuditDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "认证记录ID", required = true)
    @NotNull(message = "认证记录ID不能为空")
    private Long id;

    @ApiModelProperty(value = "审核状态：1-审核通过，2-审核拒绝", required = true)
    @NotNull(message = "审核状态不能为空")
    private Integer authStatus;

    @ApiModelProperty(value = "审核备注")
    private String auditRemark;
}
