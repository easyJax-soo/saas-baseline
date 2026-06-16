package com.baseline.system.dto;

import com.baseline.common.annotation.Dict;
import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@ApiModel(value = "SysDeptSaveDTO", description = "部门")
public class SysDeptSaveDTO {

    @ApiModelProperty(value = "部门ID,不传就是新增")
    private Long id;

    @ApiModelProperty(value = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    private String name;

    @ApiModelProperty(value = "部门编码")
    @NotBlank(message = "部门编码不能为空")
    private String code;

    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    @ApiModelProperty(value = "父路径")
    private String parentPath;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortNo;

    @ApiModelProperty(value = "负责人用户ID")
    private Long leaderUserId;

    @ApiModelProperty(value = "状态（0停用 1启用）")
    @NotNull(message = "状态不能为空")
    @Dict(dictType = "sysStatus")
    @DictVaild(dictType = "sysStatus")
    public Integer status;

    @ApiModelProperty(value = "树层级")
    private Integer level;

    @ApiModelProperty(value = "备注")
    private String remark;
}
