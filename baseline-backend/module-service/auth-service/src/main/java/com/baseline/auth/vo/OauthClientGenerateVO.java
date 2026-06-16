package com.baseline.auth.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  dto
 *
 * @author gzhc
 * @since 2024-12-02
 */

@ApiModel(value = "OauthClientGenerateVO对象", description = "")
@Data
public class OauthClientGenerateVO implements Serializable{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "客户端密钥 ")
    private String clientSecret;
}
