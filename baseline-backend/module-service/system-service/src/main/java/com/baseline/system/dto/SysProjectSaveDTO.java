package com.baseline.system.dto;

import com.baseline.common.annotation.Dict;
import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 系统项目保存DTO
 *
 * @author system
 */
@ApiModel("系统项目保存")
@Data
public class SysProjectSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目ID")
    private Long id;

    @ApiModelProperty(value = "项目编码", required = true)
    @NotBlank(message = "项目编码不能为空")
    private String code;

    @ApiModelProperty(value = "项目名称", required = true)
    @NotBlank(message = "项目名称不能为空")
    private String name;

    @ApiModelProperty(value = "项目描述")
    private String description;

    @ApiModelProperty(value = "项目类型", required = true)
    @NotBlank(message = "项目类型不能为空")
    @DictVaild(dictType = "sysProjectType")
    private String projectType;

    @ApiModelProperty(value = "项目地址")
    private String url;

    @ApiModelProperty(value = "项目Logo")
    private String logo;

    @ApiModelProperty(value = "项目图标")
    private String icon;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortNo;

    @ApiModelProperty(value = "项目状态（0停用 1启用）", required = true)
    @NotNull(message = "项目状态不能为空")
    @DictVaild(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "是否默认项目（0否 1是）")
    @DictVaild(dictType = "sysYesNo")
    private Integer isDefault;

    @ApiModelProperty(value = "打开方式（_self当前窗口 _blank新窗口）")
    @DictVaild(dictType = "sysWindowOpen")
    private String target;

    @ApiModelProperty(value = "备注")
    private String remark;
}