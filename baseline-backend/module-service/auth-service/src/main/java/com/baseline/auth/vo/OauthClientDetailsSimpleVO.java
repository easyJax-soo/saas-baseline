package com.baseline.auth.vo;


import com.alibaba.fastjson2.JSONObject;
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

@ApiModel(value = "OauthClientDetailsSimpleVO对象", description = "")
@Data
public class OauthClientDetailsSimpleVO implements Serializable{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键ID ")
    private Long id;
    @ApiModelProperty(value = "应用名称 ")
    private String name;
    @ApiModelProperty(value = "客户端ID ")
    private String clientId;
}
