package com.baseline.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 租户未绑定用户VO
 *
 * @author system
 */
@Data
@ApiModel(value = "SysTenantUnboundUserVO", description = "租户未绑定用户VO")
public class SysTenantUnboundUserVO {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户账号")
    private String account;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "用户状态：0-正常，1-停用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
