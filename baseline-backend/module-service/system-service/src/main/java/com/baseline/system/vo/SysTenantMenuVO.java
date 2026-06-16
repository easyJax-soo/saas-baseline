package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import com.baseline.common.tree.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel("租户菜单节点")
@Data
public class SysTenantMenuVO implements TreeNode<SysTenantMenuVO> {

    @ApiModelProperty(value = "菜单ID")
    private Long id;

    @ApiModelProperty(value = "租户菜单ID")
    private Long tenantMenuId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortNo;

    @ApiModelProperty(value = "路由路径")
    private String path;

    @ApiModelProperty(value = "路由类型")
    private String pathType;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    private String type;

    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;


    @ApiModelProperty(value = "菜单状态")
    private Integer visible;

    @ApiModelProperty(value = "权限标识")
    private String key;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "菜单状态（0停用 1启用）")
    @Dict(dictType = "sysStatus")
    public Integer status;

    @ApiModelProperty(value = "树层级")
    private Integer level;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "项目编码")
    private String projectCode;

    @ApiModelProperty(value = "子菜单")
    List<SysTenantMenuVO> children;

}
