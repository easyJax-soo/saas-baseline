package com.baseline.common.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserPermissionBizDTO {
    /**
     * 用户 ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 角色 ID (可选，为null时获取用户所有权限)
     */
    private Long roleId;

    /**
     * 角色 ID列表 (可选，支持多个角色ID查询)
     */
    private List<Long> roleIds;
}
