package com.baseline.system.dto;

import com.baseline.system.enums.SysRoleEnum;
import com.baseline.common.annotation.Dict;
import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
@ApiModel(value = "SysRoleSaveDTO", description = "新增编辑系统角色")
public class SysRoleSaveDTO {

    @ApiModelProperty("角色id")
    Long id;

    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
    String name;

    @ApiModelProperty("权限字符")
    @NotBlank(message = "权限字符不能为空")
    String key;

    @ApiModelProperty("数据权限")
    SysRoleEnum.DataScope dataScope;

    @ApiModelProperty("状态（0禁用 1正常）")
    @NotNull(message = "状态不能为空")
    @Dict(dictType = "sysStatus")
    @DictVaild(dictType = "sysStatus")
    Integer status;

    @ApiModelProperty("菜单id")
    List<Long> menuIds;

    @ApiModelProperty("备注")
    String remark;

    @ApiModelProperty("部门")
    List<Long> deptIds;

    @ApiModelProperty("权限")
    List<Long> permissionIds;

    @ApiModelProperty("项目编码集合")
    List<String> projectCodes;
}
