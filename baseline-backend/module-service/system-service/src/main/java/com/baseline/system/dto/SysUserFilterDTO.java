package com.baseline.system.dto;

import com.baseline.common.annotation.DictVaild;
import com.baseline.common.dto.base.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(value = "SysUserFilterDTO",description = "用户查询过滤条件")
public class SysUserFilterDTO  extends PageDTO {

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "帐号状态,1：正常 0：禁用")
    @DictVaild(dictType = "sysStatus")
    private Integer status;

    @ApiModelProperty(value = "开始日期 ")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束日期 ")
    private LocalDateTime endTime;


    @ApiModelProperty(value = "部门 ID")
    private Long deptId;

    @ApiModelProperty(value = "角色 ID")
    private Long roleId;

    @ApiModelProperty(value = "id集合", hidden = true)
    private List<Long> ids;
}
