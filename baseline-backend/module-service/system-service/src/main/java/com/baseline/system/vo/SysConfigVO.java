package com.baseline.system.vo;


import java.io.Serializable;

import com.baseline.common.annotation.Dict;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统配置 dto
 *
 * @author baseline
 * @since 2023-12-06
 */

@ApiModel(value = "SysConfigVO对象", description = "系统配置")
@Data
public class SysConfigVO implements Serializable{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID ")
    private Long id;
    @ApiModelProperty(value = "配置名称 ")
    private String name;
    @ApiModelProperty(value = "分组编码")
    private String groupCode;
    @ApiModelProperty(value = "配置键名 ")
    private String configKey;
    @ApiModelProperty(value = "配置键值 ")
    private String configValue;
    @ApiModelProperty(value = "输入框类型")
    @Dict(dictType = "sysConfigInput")
    private String inputType;
    @ApiModelProperty(value = "备注 ")
    private String remark;
}
