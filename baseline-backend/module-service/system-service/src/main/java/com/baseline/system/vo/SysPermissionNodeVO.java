package com.baseline.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baseline.common.tree.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel("权限节点")
@Data
public class SysPermissionNodeVO implements TreeNode<SysPermissionNodeVO> {

    @ApiModelProperty(value = "权限ID")
    private Long id;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortNo;

    @ApiModelProperty(value = "父权限ID")
    private Long parentId;

    @ApiModelProperty(value = "权限标识 ")
    private String permission;


    @ApiModelProperty(value = "树层级")
    private Integer level;

    @ApiModelProperty(value = "备注")
    @TableField("`remark`")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "子权限")
    List<SysPermissionNodeVO> children;

}
