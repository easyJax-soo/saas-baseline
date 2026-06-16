package com.baseline.system.dto;

import com.baseline.common.annotation.DictVaild;
import com.baseline.common.dto.base.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "SysRoleFilterDTO", description = "系统角色过滤条件")
public class SysRoleFilterDTO extends PageDTO {

    @ApiModelProperty("角色名称")
    String name;

    @ApiModelProperty("权限字符")
    String key;

    @ApiModelProperty("状态（0禁用 1正常）")
    @DictVaild(dictType = "sysStatus")
    Integer status;

    @ApiModelProperty("权限字符")
    Integer dataScope;
}
