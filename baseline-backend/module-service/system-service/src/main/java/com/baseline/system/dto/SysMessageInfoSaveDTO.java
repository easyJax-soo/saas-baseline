package com.baseline.system.dto;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baseline.system.enums.SysMessageInfoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 消息中心 dto
 *
 * @author bryant
 * @since 2025-11-25
 */

@ApiModel(value = "SysMessageInfoSaveDTO对象", description = "消息中心")
@Data
public class SysMessageInfoSaveDTO implements Serializable{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "对应事项id")
    private Long contactId;
    @ApiModelProperty(value = "消息大类")
    private Integer oneCategory;
    @ApiModelProperty(value = "消息小类")
    private Integer twoCategory;
    @ApiModelProperty(value = "阅读状态")
    private Integer readStatus;
    @ApiModelProperty(value = "用户端(1-后台,2-小程序)")
    private Integer userType;
    @ApiModelProperty(value = "消息接收人")
    private Long userId;
    @ApiModelProperty(value = "消息内容")
    private String content;
}
