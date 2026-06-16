package com.baseline.system.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author bryant
 * @date 2023/3/29
 **/
@ApiModel(value = "SysPostOptionVO对象", description = "岗位表")
@Data
public class SysPostOptionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位ID")
    private Long id;

    @ApiModelProperty(value = "岗位名称")
    private String name;

}
