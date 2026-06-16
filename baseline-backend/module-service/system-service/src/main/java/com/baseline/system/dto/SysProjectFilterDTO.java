package com.baseline.system.dto;

import com.baseline.common.annotation.DictVaild;
import com.baseline.common.dto.base.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统项目过滤DTO
 *
 * @author system
 */
@ApiModel("系统项目过滤")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysProjectFilterDTO extends PageDTO {

    @ApiModelProperty(value = "项目编码")
    private String code;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "项目类型")
    private String projectType;

    @ApiModelProperty(value = "项目状态（0停用 1启用）")
    @DictVaild(dictType = "sysStatus")
    private Integer status;
}