package com.baseline.system.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysTenantMenuFilterDTO",description = "租户菜单查询过滤条件")
public class SysTenantMenuFilterDTO implements Serializable {


    @ApiModelProperty(value = "租户菜单ID")
    private Long id;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "租户用户ID")
    private Long tenantMemberId;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

}
