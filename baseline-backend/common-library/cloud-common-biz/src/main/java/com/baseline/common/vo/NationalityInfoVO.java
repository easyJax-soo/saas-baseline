package com.baseline.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 民族信息 VO
 *
 * @author cascade
 * @date 2025/11/15
 */
@ApiModel(description="民族信息")
@Data
public class NationalityInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;

    /**
     * 民族编码
     */
    @ApiModelProperty(value="民族编码")
    private String code;

    /**
     * 民族名称
     */
    @ApiModelProperty(value="民族名称")
    private String name;
}
