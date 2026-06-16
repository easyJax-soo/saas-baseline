package com.baseline.common.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRoleBizDTO {
    /**
     * 用户 ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;
}
