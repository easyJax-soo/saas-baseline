package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value="SysOplog分页对象", description="系统日志分页")
public class SysOplogPageVO {

    @ApiModelProperty(value = "日志主键")
    private Long id;

    @ApiModelProperty(value = "模块标题")
    private String title;

    @ApiModelProperty(value = "请求方式")
    private String method;

    @ApiModelProperty(value = "操作描述")
    private String description;

    @ApiModelProperty(value = "操作人姓名")
    private String operator;

    @ApiModelProperty(value = "主机地址")
    private String ip;

    @ApiModelProperty(value = "操作状态 ")
    @Dict(dictType = "sysSuccessFail")
    private Integer status;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;
}
