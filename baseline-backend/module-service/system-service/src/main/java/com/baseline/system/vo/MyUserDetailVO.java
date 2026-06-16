package com.baseline.system.vo;


import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "MyUserDetailVO",description = "我的用户详情")
@Data
public class MyUserDetailVO {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "用户性别（0男 1女 2 保密）")
    @Dict(dictType = "sysSex")
    private Integer sex;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "帐号状态（0禁用 1正常）")
    @Dict(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "角色id集合")
    private List<SimpleRoleVO> roles;

    @ApiModelProperty(value = "当前租户ID")
    private Long currentTenantId;

    @ApiModelProperty(value = "当前租户名称")
    private String currentTenantName;

    @ApiModelProperty(value = "可访问的租户列表")
    private List<UserTenantVO> accessibleTenants;

    @ApiModelProperty(value = "是否绑定第三方登录")
    private Boolean hasThirdPartyBind;

    @ApiModelProperty(value = "是否已实名认证")
    private Boolean hasRealNameAuth;

    @ApiModelProperty(value = "实名认证状态：0-待审核，1-已认证，2-已拒绝，3-已过期")
    private Integer realNameAuthStatus;

}
