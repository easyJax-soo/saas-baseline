package com.baseline.system.dto;

import com.baseline.common.annotation.Dict;
import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Data
@ApiModel(value = "SysTenantSaveDTO",description = "租户保存信息")
public class SysTenantSaveDTO implements Serializable {


    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "租户名称")
    @NotBlank(message = "租户名称不能为空")
    private String name;

    @ApiModelProperty(value = "租户编码")
    @NotBlank(message = "租户编码不能为空")
    private String code;

    @ApiModelProperty(value = "父租户ID")
    private Long parentId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "帐号状态 ")
    @NotNull(message = "状态不能为空")
    @Dict(dictType = "sysStatus")
    @DictVaild(dictType = "sysStatus")
    private Integer status;

}
