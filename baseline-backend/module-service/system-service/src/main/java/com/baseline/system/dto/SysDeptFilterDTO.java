package com.baseline.system.dto;

import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysDeptFilterDTO",description = "部门查询过滤条件")
public class SysDeptFilterDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门 ID")
    Long id;

    @ApiModelProperty(value = "部门名称")
    String name;

    @ApiModelProperty(value = "部门编码")
    String code;

    @ApiModelProperty(value = "状态（0停用 1启用）")
    @DictVaild(dictType = "sysStatus")
    Integer status;
}
