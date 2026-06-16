package com.baseline.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 树形下拉选项VO
 * 
 * @author system
 * @date 2024-01-01
 */
@ApiModel("树形下拉选项")
@Data
@NoArgsConstructor
public class TreeOptionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "选项值")
    private String value;

    @ApiModelProperty(value = "选项标签")
    private String label;

    @ApiModelProperty(value = "子选项")
    private List<TreeOptionVO> children;

    public TreeOptionVO(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public TreeOptionVO(String value, String label, List<TreeOptionVO> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }

    /**
     * 创建树形选项
     */
    public static TreeOptionVO of(String value, String label) {
        return new TreeOptionVO(value, label);
    }

    /**
     * 创建树形选项（带子项）
     */
    public static TreeOptionVO of(String value, String label, List<TreeOptionVO> children) {
        return new TreeOptionVO(value, label, children);
    }
}
