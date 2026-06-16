package com.baseline.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 下拉选项查询DTO
 * 
 * @author system
 */
@ApiModel("下拉选项查询")
@Data
public class OptionsQueryBizDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关键词搜索（名称/编码）")
    private String keyword;

    @ApiModelProperty(value = "项目编码")
    private String projectCode;
}
