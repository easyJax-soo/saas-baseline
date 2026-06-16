package com.baseline.system.dto;

import com.baseline.common.annotation.Dict;
import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoDTO {

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    @Dict(dictType = "sysSex")
    @DictVaild(dictType = "sysSex")
    private Integer sex;

    @ApiModelProperty(value = "头像路径")
    private String avatar;
}
