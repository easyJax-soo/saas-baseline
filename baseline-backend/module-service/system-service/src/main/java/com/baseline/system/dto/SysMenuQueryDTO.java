package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单查询DTO
 *
 * @author system
 */
@ApiModel("菜单查询DTO")
@Data
public class SysMenuQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目编码，如果传入则只返回该项目的菜单/权限，不传则返回所有")
    private String projectCode;
}
