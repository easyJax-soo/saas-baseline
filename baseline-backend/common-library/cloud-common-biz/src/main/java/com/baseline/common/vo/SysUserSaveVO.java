package com.baseline.common.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "SysUserSaveDTO", description = "新增编辑系统用户")
public class SysUserSaveVO {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "部门 ID")
    private Long deptId;

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "角色id集合")
    private List<Long> roleIds;

    @ApiModelProperty(value = "岗位id集合")
    private List<Long> postIds;

    @ApiModelProperty(value = "积分值")
    private Long points;
}
