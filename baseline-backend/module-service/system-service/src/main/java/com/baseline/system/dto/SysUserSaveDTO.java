package com.baseline.system.dto;

import com.baseline.common.annotation.Dict;
import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Data
@ApiModel(value = "SysUserSaveDTO", description = "新增编辑系统用户")
public class SysUserSaveDTO {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "部门 ID")
    private Long deptId;

    @ApiModelProperty(value = "登录账号")
    @NotBlank(message = "登录账号不能为空")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    private String name;

    @ApiModelProperty(value = "用户邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(value = "手机号码")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    @Dict(dictType = "sysSex")
    @DictVaild(dictType = "sysSex")
    private Integer sex;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "帐号状态（0禁用 1正常）")
    @NotNull(message = "帐号状态不能为空")
    @DictVaild(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "角色id集合")
    private List<Long> roleIds;

    @ApiModelProperty(value = "岗位id集合")
    private List<Long> postIds;
}
