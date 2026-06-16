package com.baseline.system.vo;

import com.baseline.common.tree.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel("租户权限节点")
@Data
public class SysTenantPermissionVO implements TreeNode<SysTenantPermissionVO> {

    @ApiModelProperty(value = "权限ID ")
    private Long id;
    
    @ApiModelProperty(value = "权限名称 ")
    private String name;
    
    @ApiModelProperty(value = "权限标识 ")
    private String permission;

    @ApiModelProperty(value = "父权限ID")
    private Long parentId;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortNo;

    @ApiModelProperty(value = "树层级")
    private Integer level;

    @ApiModelProperty(value = "项目编码")
    private String projectCode;

    @ApiModelProperty(value = "子权限")
    private List<SysTenantPermissionVO> children;

}
