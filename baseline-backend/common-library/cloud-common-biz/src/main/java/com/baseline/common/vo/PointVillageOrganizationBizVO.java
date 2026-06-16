package com.baseline.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 村名组织
 * </p>
 *
 * @author bryant
 * @since 2025-08-25
 */
@Data
public class PointVillageOrganizationBizVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;



}
