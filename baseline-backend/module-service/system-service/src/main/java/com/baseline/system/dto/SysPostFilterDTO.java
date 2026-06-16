package com.baseline.system.dto;

import com.baseline.common.annotation.DictVaild;
import com.baseline.common.dto.base.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysPostFilterDTO",description = "岗位查询过滤条件")
public class SysPostFilterDTO extends PageDTO implements Serializable {

    @ApiModelProperty(value = "岗位ID")
    private Long id;

    @ApiModelProperty(value = "岗位名称")
    private String name;

    @ApiModelProperty(value = "岗位编码")
    private String code;

    @ApiModelProperty(value = "状态（0停用 1启用）")
    @DictVaild(dictType = "sysStatus")
    public Integer status;

}
