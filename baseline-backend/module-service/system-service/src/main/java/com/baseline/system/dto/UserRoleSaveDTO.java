package com.baseline.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "UserRoleSaveDTO", description = "用户分配角色")
public class UserRoleSaveDTO {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "角色id集合")
    private List<Long> roleIds;
}
