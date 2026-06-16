package com.baseline.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统操作日志业务DTO
 *
 * @author system
 */
@ApiModel("系统操作日志业务DTO")
@Data
public class SysOperLogBizDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志主键")
    private Long id;

    @ApiModelProperty(value = "模块标题")
    private String title;

    @ApiModelProperty(value = "操作描述")
    private String description;

    @ApiModelProperty(value = "方法名称class#method")
    private String method;

    @ApiModelProperty(value = "操作人员ID")
    private Long operatorId;

    @ApiModelProperty(value = "操作人姓名")
    private String operator;

    @ApiModelProperty(value = "请求URL")
    private String url;

    @ApiModelProperty(value = "主机地址")
    private String ip;

    @ApiModelProperty(value = "请求参数")
    private String params;

    @ApiModelProperty(value = "响应参数")
    private String result;

    @ApiModelProperty(value = "操作状态")
    private Integer status;

    @ApiModelProperty(value = "错误消息")
    private String errorMsg;

    @ApiModelProperty(value = "消耗时间")
    private Long costTime;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;
}
