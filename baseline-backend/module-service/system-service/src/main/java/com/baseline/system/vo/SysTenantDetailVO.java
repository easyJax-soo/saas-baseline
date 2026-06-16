package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 租户详情VO
 *
 * @author csy
 * @since 2025-11-17
 */
@ApiModel(value = "SysTenantDetailVO对象", description = "租户详情")
@Data
public class SysTenantDetailVO implements Serializable {

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

    @ApiModelProperty(value = "创建用户ID")
    private Long createUserId;

    @ApiModelProperty(value = "创建用户")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新用户ID")
    private Long updateUserId;

    @ApiModelProperty(value = "更新用户")
    private String updateUser;

    @ApiModelProperty(value = "备注")
    private String remark;

    // 关联信息
    @ApiModelProperty(value = "父租户名称")
    private String parentName;

    @ApiModelProperty(value = "子租户数量")
    private Integer childrenCount;

}
