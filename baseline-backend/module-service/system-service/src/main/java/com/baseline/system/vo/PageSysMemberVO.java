package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "PageSysMemberVO", description = "会员分页VO")
public class PageSysMemberVO {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    @Dict(dictType = "sysSex")
    private Integer sex;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "帐号状态")
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
