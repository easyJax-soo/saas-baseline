package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 字典分组请求DTO
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@ApiModel(value = "SysDictGroupDTO", description = "字典分组请求参数")
public class SysDictGroupDTO {

    @ApiModelProperty(value = "字典类型代码，不传则获取所有字典分组")
    private String code;
}