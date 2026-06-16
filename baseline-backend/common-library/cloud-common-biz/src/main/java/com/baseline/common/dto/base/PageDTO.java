package com.baseline.common.dto.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageDTO {

    @ApiModelProperty(value = "每页大小", example = "10")
    private Long pageSize = 10L;

    @ApiModelProperty(value = "页码", example = "1")
    private Long pageNum = 1L;
}
