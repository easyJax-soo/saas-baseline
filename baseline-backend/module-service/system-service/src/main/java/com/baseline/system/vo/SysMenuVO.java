package com.baseline.system.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baseline.common.annotation.Dict;
import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel("菜单")
@Data
public class SysMenuVO {

    @ApiModelProperty(value = "菜单ID")
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortNo;

    @ApiModelProperty(value = "路由类型")
    private String pathType;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "路由参数")
    private String parameter;

    @ApiModelProperty(value = "打开方式（menuItem页签 menuBlank新窗口）")
    @Dict(dictType = "sysWindowOpen")
    private String target;

    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    @Dict(dictType = "sysMenuType")
    private String type;

    @ApiModelProperty(value = "显示状态（0隐藏 1显示）")
    @Dict(dictType = "sysShowStatus")
    private Integer visible;

    @ApiModelProperty(value = "菜单状态（0停用 1启用）")
    @Dict(dictType = "sysStatus")
    public Integer status;

    @ApiModelProperty(value = "是否缓存（0不缓存 1缓存）")
    @Dict(dictType = "sysYesNo")
    public Integer cache;

    @ApiModelProperty(value = "是否外链（0否 1是）")
    @Dict(dictType = "sysYesNo")
    public Integer chain;

    @ApiModelProperty(value = "权限标识")
    private String key;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "树层级")
    private Integer level;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "删除标记")
    private Boolean deleted;

    @ApiModelProperty(value = "父路径")
    private String parentPath;


    @ApiModelProperty(value = "项目代码")
    private String projectCode;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

}
