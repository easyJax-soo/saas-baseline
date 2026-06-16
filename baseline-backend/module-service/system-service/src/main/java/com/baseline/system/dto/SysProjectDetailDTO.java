package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 系统项目详情查询DTO
 *
 * @author system
 */
@ApiModel("系统项目详情查询")
@Data
public class SysProjectDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目ID", required = true)
    @NotNull(message = "项目ID不能为空")
    private Long id;
}