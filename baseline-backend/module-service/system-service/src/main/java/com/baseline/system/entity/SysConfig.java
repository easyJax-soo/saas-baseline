package com.baseline.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 系统配置
 * </p>
 *
 * @author baseline
 * @since 2023-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_config")
@ApiModel(value="SysConfig对象", description="系统配置")
public class SysConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID ")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "配置名称 ")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "分组编码")
    @TableField("`group_code`")
    private String groupCode;

    @ApiModelProperty(value = "配置键名 ")
    @TableField("`config_key`")
    private String configKey;

    @ApiModelProperty(value = "配置键值 ")
    @TableField("`config_value`")
    private String configValue;

    @ApiModelProperty(value = "输入框类型")
    @TableField("`input_type`")
    private String inputType;

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


}
