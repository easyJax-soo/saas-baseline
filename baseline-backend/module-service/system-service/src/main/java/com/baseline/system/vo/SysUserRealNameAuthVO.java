package com.baseline.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实名认证信息VO
 *
 * @author system
 */
@ApiModel("用户实名认证信息")
@Data
public class SysUserRealNameAuthVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户账号")
    private String userAccount;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "认证类型：1-身份证，2-护照，3-港澳通行证，4-台胞证")
    private Integer authType;

    @ApiModelProperty(value = "认证类型名称")
    private String authTypeName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "证件号码（脱敏显示）")
    private String certNoMasked;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "性别：0-女，1-男")
    private Integer gender;

    @ApiModelProperty(value = "性别名称")
    private String genderName;

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

    @ApiModelProperty(value = "认证状态名称")
    private String authStatusName;

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
    private LocalDateTime createTime;
}
