package com.baseline.system.dto;

import com.baseline.common.dto.base.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 会员第三方绑定查询DTO
 * 
 * @author system
 */
@ApiModel("会员第三方绑定查询")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMemberThirdPartyBindFilterDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员ID")
    private Long memberId;

    @ApiModelProperty(value = "第三方标识")
    private String provider;
}
