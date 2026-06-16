package com.baseline.system.dto;


import java.io.Serializable;

import com.baseline.common.annotation.DictVaild;
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

@ApiModel(value = "SysConfigSaveDTO对象", description = "系统配置")
@Data
public class SysConfigSaveDTO implements Serializable{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "配置名称 ")
    private String name;
    @ApiModelProperty(value = "分组编码")
    private String groupCode;
    @ApiModelProperty(value = "配置键名 ")
    @NotBlank(message = "配置键名不能为空")
    private String configKey;
    @ApiModelProperty(value = "配置键值 ")
    private String configValue;
    @ApiModelProperty(value = "输入框类型")
    @DictVaild(dictType = "sysConfigInput")
    private String inputType;
    @ApiModelProperty(value = "备注 ")
    private String remark;
}
