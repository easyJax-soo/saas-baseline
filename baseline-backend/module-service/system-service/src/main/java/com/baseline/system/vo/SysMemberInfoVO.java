package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import com.baseline.common.vo.PointVillageOrganizationBizVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "SysMemberInfoVO", description = "会员详情VO")
public class SysMemberInfoVO {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "实名认证详细信息")
    private RealNameInfo realNameInfo;

    @ApiModelProperty(value = "村小组id")
    private Long organizeId;

    @ApiModelProperty(value = "村小组名称")
    private String organizeName;

    /**
     * 实名认证信息内部类
     */
    @Data
    @ApiModel(value = "RealNameInfo", description = "实名认证信息")
    public static class RealNameInfo {

        @ApiModelProperty(value = "真实姓名")
        private String realName;

        @ApiModelProperty(value = "手机号码")
        private String phone;

        @ApiModelProperty(value = "出生日期")
        private java.time.LocalDate birthday;

        @ApiModelProperty(value = "性别：0-女，1-男")
        @Dict(dictType = "sysSex")
        private Integer gender;

        @ApiModelProperty(value = "地址")
        private String address;
    }
}
