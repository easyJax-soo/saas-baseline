package com.baseline.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 实名认证状态枚举
 *
 * @author system
 */
@Getter
@AllArgsConstructor
public enum RealNameAuthStatusEnum {

    /**
     * 待审核
     */
    PENDING(0, "待审核"),

    /**
     * 审核通过
     */
    APPROVED(1, "审核通过"),

    /**
     * 审核拒绝
     */
    REJECTED(2, "审核拒绝"),

    /**
     * 已过期
     */
    EXPIRED(3, "已过期");

    private final Integer code;
    private final String desc;

    /**
     * 根据code获取枚举
     */
    public static RealNameAuthStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (RealNameAuthStatusEnum statusEnum : values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }
}
