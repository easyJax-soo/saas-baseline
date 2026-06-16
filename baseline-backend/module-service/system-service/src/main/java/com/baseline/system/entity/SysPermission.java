package com.baseline.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * <p>
 * 权限表
 * </p>
 *
 * @author baseline
 * @since 2023-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_permission")
@ApiModel(value="SysPermission对象", description="权限表")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限ID ")
    private Long id;

    @ApiModelProperty(value = "父ID")
    @TableField("`parent_id`")
    private Long parentId;

    @ApiModelProperty(value = "显示顺序")
    @TableField("`sort_no`")
    private Integer sortNo;

    @ApiModelProperty(value = "权限名称 ")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "权限标识 ")
    @TableField("`permission`")
    private String permission;


    @ApiModelProperty(value = "树层级")
    @TableField("`level`")
    private Integer level;

    @ApiModelProperty(value = "删除标记")
    @TableField("`deleted`")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建用户ID")
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @ApiModelProperty(value = "创建用户")
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新用户ID")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    @ApiModelProperty(value = "更新用户")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    @ApiModelProperty(value = "备注")
    @TableField("`remark`")
    private String remark;

    @ApiModelProperty(value = "项目代码")
    @TableField("`project_code`")
    private String projectCode;


}
