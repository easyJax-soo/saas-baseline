package com.baseline.system.dto;

import com.baseline.common.annotation.DictVaild;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 会员信息更新DTO
 *
 * @author system
 * @since 2024-10-04
 */
@Data
@ApiModel(value = "SysMemberInfoUpdateDTO", description = "会员信息更新DTO")
public class SysMemberInfoUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "村小组")
    private Long organizeId;

    @ApiModelProperty(value = "性别：0-女，1-男")
    @DictVaild(dictType = "sysSex")
    private Integer gender;

    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;

    @ApiModelProperty(value = "手机号码")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码格式不正确")
    private String phone;
}
