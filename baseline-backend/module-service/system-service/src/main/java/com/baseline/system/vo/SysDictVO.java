package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel(value = "SysDictVO",description = "字典数据")
@Data
public class SysDictVO {

    @ApiModelProperty(value = "字典ID")
    private Long id;

    @ApiModelProperty(value = "字典排序")
    private Integer sortNo;

    @ApiModelProperty(value = "字典标签")
    private String label;

    @ApiModelProperty(value = "字典键值")
    private String value;

    @ApiModelProperty(value = "字典编码")
    private String code;

    @ApiModelProperty(value = "是否默认（Y是 N否）")
    @Dict(dictType = "sysYesNo")
    private Integer isDefault;

    @ApiModelProperty(value = "状态")
    @Dict(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

}
