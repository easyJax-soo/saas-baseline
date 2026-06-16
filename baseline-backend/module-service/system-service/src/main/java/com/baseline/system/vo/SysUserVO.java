package com.baseline.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author bryant
 * @date 2023/3/29
 **/
@ApiModel(value = "SysUserVO对象", description = "用户下拉表")
@Data
public class SysUserVO {
    @ApiModelProperty(value = "用户id ")
    private Long id;

    @ApiModelProperty(value = "用户名 ")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "头像")
    private String avatar;
}
