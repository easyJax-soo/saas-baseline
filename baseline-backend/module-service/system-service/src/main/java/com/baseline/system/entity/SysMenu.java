package com.baseline.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baseline.common.annotation.Dict;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
@ApiModel(value="SysMenu对象", description="菜单表")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单ID")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "父菜单ID")
    @TableField("`parent_id`")
    private Long parentId;

    @ApiModelProperty(value = "显示顺序")
    @TableField("`sort_no`")
    private Integer sortNo;

    @ApiModelProperty(value = "路由类型")
    @TableField("`path_type`")
    private String pathType;

    @ApiModelProperty(value = "路由地址")
    @TableField("`path`")
    private String path;

    @ApiModelProperty(value = "组件路径")
    @TableField("`component`")
    private String component;

    @ApiModelProperty(value = "路由参数")
    @TableField("`parameter`")
    private String parameter;

    @ApiModelProperty(value = "打开方式（menuItem页签 menuBlank新窗口）")
    @TableField("`target`")
    private String target;

    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    @TableField("`type`")
    private String type;

    @ApiModelProperty(value = "显示状态（0隐藏 1显示）")
    @TableField("`visible`")
    private Integer visible;

    @ApiModelProperty(value = "菜单状态（0停用 1启用）")
    @TableField("`status`")
    public Integer status;

    @ApiModelProperty(value = "是否缓存（0不缓存 1缓存）")
    @TableField("`cache`")
    public Integer cache;

    @ApiModelProperty(value = "是否外链（0否 1是）")
    @TableField("`chain`")
    public Integer chain;

    @ApiModelProperty(value = "权限标识")
    @TableField("`key`")
    private String key;

    @ApiModelProperty(value = "菜单图标")
    @TableField("`icon`")
    private String icon;

    @ApiModelProperty(value = "树层级")
    @TableField("`level`")
    private Integer level;

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

    @ApiModelProperty(value = "删除标记")
    @TableField("`deleted`")
    private Boolean deleted;

    @ApiModelProperty(value = "父路径")
    @TableField("`parent_path`")
    private String parentPath;


    @ApiModelProperty(value = "项目代码")
    @TableField("`project_code`")
    private String projectCode;
}
