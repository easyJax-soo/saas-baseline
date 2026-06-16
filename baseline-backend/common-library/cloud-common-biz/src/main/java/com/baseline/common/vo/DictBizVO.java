package com.baseline.common.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 角色表 sys_role
 *
 * @author ruoyi
 */
@Data
public class DictBizVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 字典文本 */
    private String label;

    /** 字典枚举值 */
    private String value;
}
