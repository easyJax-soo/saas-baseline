package com.baseline.system.dto;

import com.baseline.common.annotation.DictVaild;
import com.baseline.common.dto.base.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysDictDataFilterDTO",description = "字典查询过滤条件")
public class SysDictDataFilterDTO extends PageDTO {

    @ApiModelProperty("字典标签")
    String label;
    @ApiModelProperty("字典类型编码")
    String code;
    @ApiModelProperty("字典状态,1：正常 0：禁用")
    @DictVaild(dictType = "sysStatus")
    Integer status;

}
