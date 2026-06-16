package com.baseline.system.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "SysTenantMenuSaveDTO",description = "租户菜单保存信息")
@Data
public class SysTenantMenuSaveDTO implements Serializable {


    @ApiModelProperty(value = "租户ID")
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;

    @ApiModelProperty(value = "菜单ID")
    @NotEmpty(message = "菜单ID不能为空")
    private List<Long> menuIds;

}
