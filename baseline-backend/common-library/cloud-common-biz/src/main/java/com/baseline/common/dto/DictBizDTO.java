package com.baseline.common.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DictBizDTO {
    /**
     * 字典类型编码
     */
    @NotNull(message = "字典类型编码")
    private String code;
}
