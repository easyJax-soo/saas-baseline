package com.baseline.common.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 角色表 sys_role
 *
 * @author ruoyi
 */
@Data
public class UserRoleBizVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    private Long id;

    /** 角色名称 */
    private String name;

    /** 角色权限 */
    private String key;

    /** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限） */
    private Integer dataScope;

    /** 角色状态（0正常 1停用） */
    private String status;
}
