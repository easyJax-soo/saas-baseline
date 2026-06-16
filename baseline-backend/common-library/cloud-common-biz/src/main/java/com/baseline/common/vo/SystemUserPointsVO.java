package com.baseline.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author bryant
 * @date 2025/9/3
 **/
@Data
public class SystemUserPointsVO {
    @ApiModelProperty(value = "用户名称")
    private String name;
    @ApiModelProperty(value = "用户账号 ")
    private String account;
    @ApiModelProperty(value = "积分")
    private Long points;
}
