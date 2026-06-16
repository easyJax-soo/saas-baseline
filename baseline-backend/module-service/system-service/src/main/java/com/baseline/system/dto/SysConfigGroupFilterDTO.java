package com.baseline.system.dto;

import com.baseline.common.dto.base.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统配置分组查询DTO
 *
 * @author baseline
 * @since 2023-12-06
 */
@ApiModel(value = "SysConfigGroupFilterDTO对象", description = "系统配置分组查询")
@Data
public class SysConfigGroupFilterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分组名称")
    private String name;

    @ApiModelProperty(value = "配置分组编码")
    private String groupCode;
}
