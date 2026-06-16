package com.baseline.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baseline.system.enums.SysRoleEnum;
import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value="SysRolePageVO分页对象", description="系统角色分页")
public class SysRolePageVO {
    @ApiModelProperty(value = "角色ID")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色权限字符串")
    @TableField("`key`")
    private String key;

    @ApiModelProperty(value = "角色状态 ")
    @Dict(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("数据权限")
    private SysRoleEnum.DataScope dataScope;
}
