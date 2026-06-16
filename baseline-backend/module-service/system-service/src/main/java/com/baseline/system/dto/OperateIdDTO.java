package com.baseline.system.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author bryant
 * @date 2025/11/26
 **/
@Data
public class OperateIdDTO {
    @NotNull(message = "id不能为空")
    private Long id;
}
