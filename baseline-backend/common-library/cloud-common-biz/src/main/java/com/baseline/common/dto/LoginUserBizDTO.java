package com.baseline.common.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserBizDTO {
    /**
     * 用户名，账号
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
}
