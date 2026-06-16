package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PageSysUserVO {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "部门ID")
    private String deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "帐号状态 ")
    @Dict(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


    @ApiModelProperty(value = "是否绑定第三方登录")
    private Boolean hasThirdPartyBind;

    @ApiModelProperty(value = "是否已实名认证")
    private Boolean hasRealNameAuth;

    @ApiModelProperty(value = "实名认证状态：0-待审核，1-已认证，2-已拒绝，3-已过期")
    private Integer realNameAuthStatus;
}
