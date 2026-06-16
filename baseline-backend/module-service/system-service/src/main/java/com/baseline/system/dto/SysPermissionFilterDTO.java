package com.baseline.system.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 权限表 dto
 *
 * @author baseline
 * @since 2023-12-05
 */

@ApiModel(value = "SysPermissionFilterDTO对象", description = "权限表")
@Data
public class SysPermissionFilterDTO implements Serializable{
    private static final long serialVersionUID=1L;


    @ApiModelProperty(value = "权限名称 ")
    private String name;
    @ApiModelProperty(value = "权限标识 ")
    private String permission;

    @ApiModelProperty(value = "项目代码")
    private String projectCode;
}
