package com.baseline.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author bryant
 * @date 2025/8/28
 **/
@Data
public class HandlePointsDTO {
    @NotNull
    private Long userId;

    //true为减分，false为加分
    private boolean isDeduct;

    private Long points;
}
