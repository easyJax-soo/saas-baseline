package com.baseline.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户第三方绑定信息VO
 * 
 * @author system
 */
@ApiModel("用户第三方绑定信息")
@Data
public class SysUserThirdPartyBindVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "第三方标识")
    private String provider;

    @ApiModelProperty(value = "第三方名称")
    private String providerName;

    @ApiModelProperty(value = "第三方用户ID")
    private String thirdPartyUserId;

    @ApiModelProperty(value = "平台用户ID")
    private Long userId;

    @ApiModelProperty(value = "平台用户名称")
    private String userName;

    @ApiModelProperty(value = "绑定时间")
    private LocalDateTime bindTime;
}
