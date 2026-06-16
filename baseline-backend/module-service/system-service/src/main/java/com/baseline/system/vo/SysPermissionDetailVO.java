package com.baseline.system.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限表 dto
 *
 * @author baseline
 * @since 2023-12-05
 */

@ApiModel(value = "SysPermissionDetailVO对象", description = "权限表")
@Data
public class SysPermissionDetailVO implements Serializable{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "权限ID ")
    private Long id;
    @ApiModelProperty(value = "权限名称 ")
    private String name;
    @ApiModelProperty(value = "权限标识 ")
    private String permission;

    @ApiModelProperty(value = "父ID")
    private Long parentId;

    @ApiModelProperty(value = "树层级")
    private Integer level;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortNo;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
