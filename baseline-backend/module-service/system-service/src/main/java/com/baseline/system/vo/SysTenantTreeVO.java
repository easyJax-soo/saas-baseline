package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 租户树形结构VO
 *
 * @author csy
 * @since 2025-11-17
 */
@ApiModel(value = "SysTenantTreeVO对象", description = "租户树形结构")
@Data
public class SysTenantTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户ID")
    private Long id;

    @ApiModelProperty(value = "租户名称")
    private String name;

    @ApiModelProperty(value = "租户编码")
    private String code;

    @ApiModelProperty(value = "父租户ID")
    private Long parentId;

    @ApiModelProperty(value = "租户层级")
    private Integer level;

    @ApiModelProperty(value = "层级路径")
    private String parentPath;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "帐号状态")
    @Dict(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    // 树形结构相关字段
    @ApiModelProperty(value = "子租户列表")
    private List<SysTenantTreeVO> children;

    @ApiModelProperty(value = "父租户名称")
    private String parentName;

    @ApiModelProperty(value = "是否有子节点")
    private Boolean hasChildren;

}
