package com.baseline.system.vo;

import com.baseline.common.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author bryant
 * @date 2025/11/26
 **/
@Data
public class SysMessageTypeNumVO {
    @ApiModelProperty(value = "消息大类")
    @Dict(dictType = "messageOneCategory")
    private Integer oneCategory;

    @ApiModelProperty(value = "数量")
    private Long count;
}
