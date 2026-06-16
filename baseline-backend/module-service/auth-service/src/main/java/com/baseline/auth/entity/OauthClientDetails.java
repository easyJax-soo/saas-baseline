package com.baseline.auth.entity;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * <p>
 *
 * </p>
 *
 * @author gzhc
 * @since 2024-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_client_details")
@ApiModel(value="OauthClientDetails对象", description="")
public class OauthClientDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID ")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "应用名称 ")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "客户端ID ")
    @TableField("`client_id`")
    private String clientId;

    @ApiModelProperty(value = "客户端密钥 ")
    @TableField("`client_secret`")
    private String clientSecret;

    @ApiModelProperty(value = "资源id集合，多个资源用英文逗号隔开")
    @TableField("`resource_ids`")
    private String[] resourceIds;

    @ApiModelProperty(value = "作用域 ")
    @TableField("`scope`")
    private String scope;

    @ApiModelProperty(value = "授权类型 ")
    @TableField("`authorized_grant_types`")
    private String[] authorizedGrantTypes;

    @ApiModelProperty(value = "重定向URI ")
    @TableField("`web_server_redirect_uri`")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "权限列表 ")
    @TableField("`authorities`")
    private String authorities;

    @ApiModelProperty(value = "访问令牌有效期 ")
    @TableField("`access_token_validity`")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "刷新令牌有效期 ")
    @TableField("`refresh_token_validity`")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "预留字段，格式必须是json")
    @TableField("`additional_information`")
    private JSONObject additionalInformation;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建用户ID")
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @ApiModelProperty(value = "创建用户")
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新用户ID")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    @ApiModelProperty(value = "更新用户")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    @ApiModelProperty(value = "自动批准 ")
    @TableField("`autoapprove`")
    private String autoapprove;


}
