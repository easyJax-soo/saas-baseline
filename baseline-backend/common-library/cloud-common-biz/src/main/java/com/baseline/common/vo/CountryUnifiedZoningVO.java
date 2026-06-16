package com.baseline.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 国家统一区划 VO
 *
 * @author cascade
 * @date 2025/11/15
 */
@ApiModel(description="国家统一区划")
@Data
public class CountryUnifiedZoningVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;

    /**
     * 编码
     */
    @ApiModelProperty(value="编码")
    private String areaCode;

    /**
     * 父级编码
     */
    @ApiModelProperty(value="父级编码")
    private String parentCode;

    /**
     * 地区
     */
    @ApiModelProperty(value="地区")
    private String name;

    /**
     * 级别
     */
    @ApiModelProperty(value="级别")
    private Integer level;
}
