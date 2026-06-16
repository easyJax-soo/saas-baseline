package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author bryant
 * @date 2023/3/29
 **/
@ApiModel(value = "SysTenantUserDetailVO对象", description = "租户用户信息")
@Data
public class SysTenantUserDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户用户ID")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "是否为租户管理员（0普通用户 1租户管理员）")
    @Dict(dictType = "sysTenantUserType")
    private Integer isTenantAdmin;

}
