package com.baseline.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色和项目关联表
 *
 * @author system
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_project")
public class SysRoleProject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 项目编码
     */
    private String projectCode;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
