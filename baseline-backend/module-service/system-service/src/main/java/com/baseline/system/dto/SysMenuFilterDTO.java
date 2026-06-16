package com.baseline.system.dto;

import com.baseline.common.annotation.Dict;
import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysMenuFilterDTO {

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单状态（0停用 1启用）")
    @DictVaild(dictType = "sysStatus")
    public Integer status;

    @ApiModelProperty(value = "项目代码")
    private String projectCode;

}
