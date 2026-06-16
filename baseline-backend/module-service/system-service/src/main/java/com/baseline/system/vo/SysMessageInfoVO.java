package com.baseline.system.vo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baseline.common.annotation.Dict;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 消息中心 dto
 *
 * @author bryant
 * @since 2025-11-25
 */

@ApiModel(value = "SysMessageInfoVO对象", description = "消息中心")
@Data
public class SysMessageInfoVO implements Serializable{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "对应事项id")
    private Long contactId;
    @ApiModelProperty(value = "消息大类")
    @Dict(dictType = "messageOneCategory")
    private Integer oneCategory;
    @ApiModelProperty(value = "消息小类")
    @Dict(dictType = "messageTwoCategory")
    private Integer twoCategory;
    @ApiModelProperty(value = "阅读状态")
    @Dict(dictType = "messageReadStatus")
    private Integer readStatus;
    @ApiModelProperty(value = "消息接收人")
    private Long userId;
    @ApiModelProperty(value = "消息内容")
    private String content;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
