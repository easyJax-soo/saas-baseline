package com.baseline.system.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baseline.common.annotation.DictVaild;
import com.baseline.common.dto.base.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "SysTenantFilterDTO",description = "租户查询过滤条件")
public class SysTenantFilterDTO extends PageDTO implements Serializable {


    @ApiModelProperty(value = "租户ID")
    private Long id;

    @ApiModelProperty(value = "租户名称")
    private String name;

    @ApiModelProperty(value = "租户编码")
    private String code;

    @ApiModelProperty(value = "父租户ID")
    private Long parentId;

    @ApiModelProperty(value = "租户层级")
    private Integer level;

    @ApiModelProperty(value = "帐号状态 ")
    @DictVaild(dictType = "sysStatus")
    private Integer status;

}
