package com.baseline.auth.dto;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *  dto
 *
 * @author gzhc
 * @since 2024-12-02
 */

@ApiModel(value = "OauthClientDetailsFilterDTO对象", description = "")
@Data
public class OauthClientDetailsFilterDTO implements Serializable{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "应用名称 ")
    private String name;
}
