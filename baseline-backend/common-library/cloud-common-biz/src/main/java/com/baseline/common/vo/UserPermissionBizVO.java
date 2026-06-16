package com.baseline.common.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 角色表 sys_role
 *
 * @author ruoyi
 */
@Data
public class UserPermissionBizVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 权限ID */
    private Long id;
    /** 权限名词 */
    private String name;
    /** 权限标识 */
    private String permission;
}
