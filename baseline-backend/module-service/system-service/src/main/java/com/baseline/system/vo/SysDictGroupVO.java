package com.baseline.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 字典分组VO，包含字典类型及其对应的字典数据项
 *
 * @author system
 * @since 2024-01-01
 */
@Data
@ApiModel(value = "SysDictGroupVO", description = "字典分组对象")
public class SysDictGroupVO {

    @ApiModelProperty(value = "字典名称")
    private String name;

    @ApiModelProperty(value = "字典代码")
    private String code;

    @ApiModelProperty(value = "字典数据项列表")
    private List<SysDictDataVO> dictDataList;

    /**
     * 字典数据项VO
     */
    @Data
    @ApiModel(value = "SysDictDataVO", description = "字典数据项")
    public static class SysDictDataVO {

        @ApiModelProperty(value = "字典标签")
        private String label;

        @ApiModelProperty(value = "字典键值")
        private String value;
    }
}
