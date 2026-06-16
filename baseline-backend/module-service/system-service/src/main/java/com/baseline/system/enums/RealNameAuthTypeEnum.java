package com.baseline.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 实名认证类型枚举
 *
 * @author system
 */
@Getter
@AllArgsConstructor
public enum RealNameAuthTypeEnum {

    /**
     * 身份证
     */
    ID_CARD(1, "身份证"),

    /**
     * 护照
     */
    PASSPORT(2, "护照"),

    /**
     * 港澳通行证
     */
    HK_MACAO_PASS(3, "港澳通行证"),

    /**
     * 台胞证
     */
    TAIWAN_PASS(4, "台胞证");

    private final Integer code;
    private final String desc;

    /**
     * 根据code获取枚举
     */
    public static RealNameAuthTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (RealNameAuthTypeEnum typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }
}
