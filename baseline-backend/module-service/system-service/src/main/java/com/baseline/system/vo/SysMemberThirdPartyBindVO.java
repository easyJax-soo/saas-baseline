package com.baseline.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会员第三方绑定信息VO
 * 
 * @author system
 */
@ApiModel("会员第三方绑定信息")
@Data
public class SysMemberThirdPartyBindVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "第三方标识")
    private String provider;

    @ApiModelProperty(value = "第三方名称")
    private String providerName;

    @ApiModelProperty(value = "第三方用户ID")
    private String thirdPartyUserId;

    @ApiModelProperty(value = "平台会员ID")
    private Long memberId;

    @ApiModelProperty(value = "平台会员名称")
    private String memberName;

    @ApiModelProperty(value = "绑定时间")
    private LocalDateTime bindTime;
}
