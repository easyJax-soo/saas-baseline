package com.baseline.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员实名认证信息表
 * </p>
 *
 * @author system
 * @since 2024-10-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_member_real_name_auth")
@ApiModel(value="SysMemberRealNameAuth对象", description="会员实名认证信息表")
public class SysMemberRealNameAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "会员ID")
    private Long memberId;

    @ApiModelProperty(value = "认证类型：1-身份证，2-护照，3-港澳通行证，4-台胞证")
    private Integer authType;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "证件号码（加密存储）")
    private String certNo;

    @ApiModelProperty(value = "证件号码哈希值（用于查重）")
    private String certNoHash;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "性别：0-女，1-男")
    private Integer gender;

    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "证件正面照片URL")
    private String frontImageUrl;

    @ApiModelProperty(value = "证件反面照片URL")
    private String backImageUrl;

    @ApiModelProperty(value = "人脸照片URL")
    private String faceImageUrl;

    @ApiModelProperty(value = "认证状态：0-待审核，1-审核通过，2-审核拒绝，3-已过期")
    private Integer authStatus;

    @ApiModelProperty(value = "提交时间")
    private LocalDateTime submitTime;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime auditTime;

    @ApiModelProperty(value = "审核人ID")
    private Long auditorId;

    @ApiModelProperty(value = "审核人姓名")
    private String auditorName;

    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

    @ApiModelProperty(value = "认证过期时间")
    private LocalDateTime expireTime;

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

    @ApiModelProperty(value = "删除标识：0-未删除，1-已删除")
    private Integer deleted;
}
