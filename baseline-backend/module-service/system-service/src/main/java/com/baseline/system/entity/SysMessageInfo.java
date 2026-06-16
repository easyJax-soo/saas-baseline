package com.baseline.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * <p>
 * 消息中心
 * </p>
 *
 * @author bryant
 * @since 2025-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_message_info")
@ApiModel(value="SysMessageInfo对象", description="消息中心")
public class SysMessageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "对应事项id")
    @TableField("`contact_id`")
    private Long contactId;

    @ApiModelProperty(value = "消息大类")
    @TableField("`one_category`")
    @Dict(dictType = "messageOneCategory")
    private Integer oneCategory;

    @ApiModelProperty(value = "消息小类")
    @TableField("`two_category`")
    @Dict(dictType = "messageTwoCategory")
    private Integer twoCategory;

    @ApiModelProperty(value = "阅读状态")
    @TableField("`read_status`")
    @Dict(dictType = "messageReadStatus")
    private Integer readStatus;

    @ApiModelProperty(value = "用户端(1-后台,2-小程序)")
    @TableField("`user_type`")
    private Integer userType;

    @ApiModelProperty(value = "消息接收人")
    @TableField("`user_id`")
    private Long userId;

    @ApiModelProperty(value = "消息内容")
    @TableField("`content`")
    private String content;

    @ApiModelProperty(value = "删除标识")
    @TableField("`deleted`")
    private Byte deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
