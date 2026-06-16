package com.baseline.system.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baseline.common.dto.base.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysTenantUserFilterDTO",description = "租户用户查询过滤条件")
public class SysTenantUserFilterDTO extends PageDTO implements Serializable {


    @ApiModelProperty(value = "租户用户ID")
    private Long id;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "系统用户 ID ")
    private Long userId;

    @ApiModelProperty(value = "是否为租户管理员（0普通用户 1租户管理员） ")
    private Integer isTenantAdmin;

}
