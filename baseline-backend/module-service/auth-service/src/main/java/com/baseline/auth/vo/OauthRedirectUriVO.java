package com.baseline.auth.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "OauthRedirectUriVO", description = "获取oauth跳转地址")
@Data
public class OauthRedirectUriVO {
    private String redirectUri;
}
