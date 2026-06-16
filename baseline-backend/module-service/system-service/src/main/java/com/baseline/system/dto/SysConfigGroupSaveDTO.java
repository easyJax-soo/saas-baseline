package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 系统配置分组保存DTO
 *
 * @author baseline
 * @since 2023-12-06
 */
@ApiModel(value = "SysConfigGroupSaveDTO对象", description = "系统配置分组保存")
@Data
public class SysConfigGroupSaveDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "分组名称")
    @NotBlank(message = "分组名称不能为空")
    private String name;

    @ApiModelProperty(value = "配置分组编码")
    @NotBlank(message = "分组编码不能为空")
    private String groupCode;

    @ApiModelProperty(value = "系统默认，不允许删除")
    private Integer sysDefault;

    @ApiModelProperty(value = "备注")
    private String remark;
}
