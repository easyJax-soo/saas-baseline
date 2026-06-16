package com.baseline.system.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baseline.system.entity.SysMenu;
import com.baseline.common.annotation.Dict;
import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class SysMenuSaveDTO {
    @ApiModelProperty(value = "菜单ID,不传就是新增")
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
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
//    @DictVaild(dictType = "sysWindowOpen")
    private String target;

    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    @DictVaild(dictType = "sysMenuType")
    private String type;

    @ApiModelProperty(value = "显示状态（0隐藏 1显示）")
    private Integer visible;

    @ApiModelProperty(value = "菜单状态（0停用 1启用）")
    @Dict(dictType = "sysStatus")
    @DictVaild(dictType = "sysStatus")
    public Integer status;

    @ApiModelProperty(value = "是否缓存（0不缓存 1缓存）")
    @Dict(dictType = "sysYesNo")
    @DictVaild(dictType = "sysYesNo")
    public Integer cache;

    @ApiModelProperty(value = "是否外链（0否 1是）")
    @Dict(dictType = "sysYesNo")
    @DictVaild(dictType = "sysYesNo")
    public Integer chain;

    @ApiModelProperty(value = "权限标识")
    private String key;

    @ApiModelProperty(value = "菜单图标")
    private String icon;


    @ApiModelProperty(value = "备注")
    private String remark;


    @ApiModelProperty(value = "项目代码")
    private String projectCode;

}
