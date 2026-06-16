package com.baseline.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统项目表
 *
 * @author system
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_project")
public class SysProject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 项目编码
     */
    private String code;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 项目地址
     */
    private String url;

    /**
     * 项目Logo
     */
    private String logo;

    /**
     * 项目图标
     */
    private String icon;

    /**
     * 显示顺序
     */
    private Integer sortNo;

    /**
     * 项目状态（0停用 1启用）
     */
    private Integer status;

    /**
     * 是否默认项目（0否 1是）
     */
    private Integer isDefault;

    /**
     * 打开方式（_self当前窗口 _blank新窗口）
     */
    private String target;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建用户
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 更新用户ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 更新用户
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标记
     */
    private Integer deleted;
}