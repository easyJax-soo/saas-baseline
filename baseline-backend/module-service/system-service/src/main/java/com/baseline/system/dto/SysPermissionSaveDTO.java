package com.baseline.system.dto;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import jakarta.validation.constraints.NotBlank;

/**
 * 权限表 dto
 *
 * @author baseline
 * @since 2023-12-05
 */

@ApiModel(value = "SysPermissionSaveDTO对象", description = "权限表")
@Data
public class SysPermissionSaveDTO implements Serializable{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "权限ID ")
    private Long id;
    @ApiModelProperty(value = "权限名称 ")
    @NotBlank(message = "权限名称不能为空")
    private String name;
    @ApiModelProperty(value = "权限标识 ")
    private String permission;

    @ApiModelProperty(value = "父ID")
    private Long parentId;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortNo;

    @ApiModelProperty(value = "项目代码")
    private String projectCode;
}
