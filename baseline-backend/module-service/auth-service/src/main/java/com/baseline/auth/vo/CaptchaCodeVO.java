package com.baseline.auth.vo;

import lombok.Data;

@Data
public class CaptchaCodeVO {
    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 验证码
     */
    private String image;

}
