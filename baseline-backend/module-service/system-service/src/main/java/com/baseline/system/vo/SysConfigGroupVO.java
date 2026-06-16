package com.baseline.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统配置分组VO
 *
 * @author baseline
 * @since 2023-12-06
 */
@ApiModel(value = "SysConfigGroupVO对象", description = "系统配置分组")
@Data
public class SysConfigGroupVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "分组名称")
    private String name;

    @ApiModelProperty(value = "配置分组编码")
    private String groupCode;

    @ApiModelProperty(value = "系统默认，不允许删除")
    private Integer sysDefault;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}
