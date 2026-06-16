package com.baseline.system.dto;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baseline.common.annotation.Dict;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 消息中心 dto
 *
 * @author bryant
 * @since 2025-11-25
 */

@ApiModel(value = "SysMessageInfoFilterDTO对象", description = "消息中心")
@Data
public class SysMessageInfoFilterDTO implements Serializable{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "消息大类")
    @Dict(dictType = "messageOneCategory")
    private Integer oneCategory;

    private Integer userType;

    private Long userId;

}
