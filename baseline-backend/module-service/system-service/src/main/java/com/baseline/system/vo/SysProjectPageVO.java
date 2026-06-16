package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统项目分页VO
 *
 * @author system
 */
@ApiModel("系统项目分页")
@Data
public class SysProjectPageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目ID")
    private Long id;

    @ApiModelProperty(value = "项目编码")
    private String code;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "项目描述")
    private String description;

    @ApiModelProperty(value = "项目类型")
    @Dict(dictType = "sysProjectType")
    private String projectType;

    @ApiModelProperty(value = "项目地址")
    private String url;

    @ApiModelProperty(value = "项目Logo")
    private String logo;

    @ApiModelProperty(value = "项目图标")
    private String icon;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortNo;

    @ApiModelProperty(value = "项目状态（0停用 1启用）")
    @Dict(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "是否默认项目（0否 1是）")
    @Dict(dictType = "sysYesNo")
    private Integer isDefault;

    @ApiModelProperty(value = "打开方式（_self当前窗口 _blank新窗口）")
    @Dict(dictType = "sysWindowOpen")
    private String target;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}