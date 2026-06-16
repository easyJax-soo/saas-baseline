package com.baseline.system.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baseline.common.annotation.Dict;
import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@ApiModel(value = "SysDictTypeSaveDTO", description = "保存字典类型")
public class SysDictTypeSaveDTO {
    @ApiModelProperty(value = "字典主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "字典名称")
    @NotBlank(message = "字典名称不能为空")
    private String name;

    @ApiModelProperty(value = "字典代码")
    @NotBlank(message = "字典代码不能为空")
    private String code;

    @ApiModelProperty(value = "状态,1：正常 0：禁用")
    @NotNull(message = "状态不能为空")
    @Dict(dictType = "sysStatus")
    @DictVaild(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
