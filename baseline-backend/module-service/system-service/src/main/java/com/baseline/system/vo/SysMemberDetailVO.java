package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "SysMemberDetailVO", description = "会员详情VO")
public class SysMemberDetailVO {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    @Dict(dictType = "sysSex")
    private Integer sex;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "帐号状态")
    @Dict(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建用户")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新用户")
    private String updateUser;

    @ApiModelProperty(value = "是否绑定第三方登录")
    private Boolean hasThirdPartyBind;

    @ApiModelProperty(value = "是否已实名认证")
    private Boolean hasRealNameAuth;

    @ApiModelProperty(value = "实名认证状态：0-待审核，1-已认证，2-已拒绝，3-已过期")
    private Integer realNameAuthStatus;
}
