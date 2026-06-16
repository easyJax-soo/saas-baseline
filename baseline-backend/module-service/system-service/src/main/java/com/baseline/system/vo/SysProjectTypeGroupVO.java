package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 按项目类型分组的VO
 *
 * @author system
 */
@ApiModel("按项目类型分组的项目")
@Data
public class SysProjectTypeGroupVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目类型")
    @Dict(dictType = "sysProjectType")
    private String projectType;

    @ApiModelProperty(value = "该类型下的项目列表")
    private List<SysProjectVO> projects;

    @ApiModelProperty(value = "项目数量")
    private Integer count;
}
