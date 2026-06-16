package com.baseline.auth.dto;


import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *  dto
 *
 * @author gzhc
 * @since 2024-12-02
 */

@ApiModel(value = "OauthClientDetailsSaveDTO对象", description = "")
@Data
public class OauthClientDetailsSaveDTO implements Serializable{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键ID ")
    private Long id;
    @ApiModelProperty(value = "应用名称 ")
    private String name;
    @ApiModelProperty(value = "客户端ID ")
    private String clientId;
    @ApiModelProperty(value = "客户端密钥 ")
    private String clientSecret;
    @ApiModelProperty(value = "作用域 ")
    private String scope;
    @ApiModelProperty(value = "授权类型 ")
    private String[] authorizedGrantTypes;
    @ApiModelProperty(value = "重定向URI ")
    private String webServerRedirectUri;
    @ApiModelProperty(value = "权限列表 ")
    private String authorities;
    @ApiModelProperty(value = "访问令牌有效期 ")
    private Integer accessTokenValidity;
    @ApiModelProperty(value = "刷新令牌有效期 ")
    private Integer refreshTokenValidity;
    @ApiModelProperty(value = "附加信息 ")
    private JSONObject additionalInformation;
}
