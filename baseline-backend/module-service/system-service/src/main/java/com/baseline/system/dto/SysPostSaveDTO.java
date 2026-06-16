package com.baseline.system.dto;

import com.baseline.common.annotation.Dict;
import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@Data
@ApiModel(value = "SysPostSaveDTO",description = "岗位保存信息")
public class SysPostSaveDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位ID")
    private Long id;

    @ApiModelProperty(value = "岗位名称")
    @NotBlank(message = "岗位名称不能为空")
    private String name;

    @ApiModelProperty(value = "岗位编码")
    @NotBlank(message = "岗位编码不能为空")
    private String code;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortNo;

    @ApiModelProperty(value = "状态（0停用 1启用）")
    @Dict(dictType = "sysStatus")
    @DictVaild(dictType = "sysStatus")
    public Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
