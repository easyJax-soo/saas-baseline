package com.baseline.system.dto;

import com.baseline.common.annotation.DictVaild;
import com.baseline.common.dto.base.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "SysOplogFilterDTO", description = "操作日志分页过滤条件")
public class SysOplogFilterDTO extends PageDTO {

    @ApiModelProperty("系统模块")
    String title;

    @ApiModelProperty("操作人员")
    String operator;

    @ApiModelProperty("操作描述")
    String description;

    @ApiModelProperty("状态（0失败 1成功）")
    @DictVaild(dictType = "sysStatus")
    Integer status;

    @ApiModelProperty("开始日期")
    LocalDateTime beginTime;

    @ApiModelProperty("结束日期")
    LocalDateTime endTime;
}
