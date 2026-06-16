package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import com.baseline.common.tree.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "SysDeptNode",description = "部门节点")
@Data
public class SysDeptNodeVO implements TreeNode<SysDeptNodeVO> {

    @ApiModelProperty(value = "部门ID")
    private Long id;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "部门编码")
    private String code;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortNo;

    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    @ApiModelProperty(value = "部门层级")
    private Integer level;

    @ApiModelProperty(value = "负责人用户ID")
    private Long leaderUserId;

    @ApiModelProperty(value = "负责人姓名")
    private String leaderUserName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "菜单状态（0停用 1启用）")
    @Dict(dictType = "sysStatus")
    public Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "子菜单")
    List<SysDeptNodeVO> children;

}
