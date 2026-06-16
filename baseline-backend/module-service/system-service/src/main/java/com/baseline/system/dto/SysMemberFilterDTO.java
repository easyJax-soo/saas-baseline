package com.baseline.system.dto;

import com.baseline.common.annotation.DictVaild;
import com.baseline.common.dto.base.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "SysMemberFilterDTO", description = "会员查询过滤条件")
public class SysMemberFilterDTO extends PageDTO {

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "帐号状态,1：正常 0：禁用")
    @DictVaild(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    @DictVaild(dictType = "sysSex")
    private Integer sex;

    @ApiModelProperty(value = "开始日期")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束日期")
    private LocalDateTime endTime;
}
