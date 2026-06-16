package com.baseline.common.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 消息中心 dto
 *
 * @author bryant
 * @since 2025-11-25
 */

@ApiModel(value = "SysMessageInfoSaveDTO对象", description = "消息中心")
@Data
public class SysMessageInfoSaveTypeBizDTO implements Serializable{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "按照角色还是用户(1-用户，2-角色，3-全部)")
    private Integer saveType;
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
    private List<Long> userIds;
    @ApiModelProperty(value = "消息接收角色")
    private List<Long> roleIds;
    @ApiModelProperty(value = "消息内容")
    private String content;
}
