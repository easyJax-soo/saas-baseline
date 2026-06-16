package com.baseline.system.dto;


import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import jakarta.validation.constraints.NotBlank;

/**
 * 系统配置 dto
 *
 * @author baseline
 * @since 2023-12-06
 */

@ApiModel(value = "SysConfigFilterDTO对象", description = "系统配置")
@Data
public class SysConfigFilterDTO implements Serializable{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "配置键名 ")
    @NotBlank(message = "配置键名不能为空")
    private String configKey;
    @ApiModelProperty(value = "分组编码")
    @NotBlank(message = "分组编码不能为空")
    private String groupCode;
}
