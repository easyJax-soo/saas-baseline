package com.baseline.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CaptchaEnabledVO {
    private static final long serialVersionUID = 1L;


    /**
     * 是否开启
     */
    @ApiModelProperty("是否开启开启验证码认证，true=开启 false=未开启")
    private boolean isEnabled;

}
