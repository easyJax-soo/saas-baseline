package com.baseline.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baseline.core.annotation.IEnumerator;

import java.util.Arrays;

/**
 * @author bryant
 * @date 2025/11/25
 **/
public class SysMessageInfoEnum {
    public enum SaveType implements IEnumerator {
        USER(1, "用户"), ROLE(2, "角色"), ALL(3, "全部");
        @EnumValue
        private final Integer code;
        private final String description;

        SaveType(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        public static SysMessageInfoEnum.SaveType codeOf(Integer code) {
            return Arrays.stream(SysMessageInfoEnum.SaveType.values())
                    .filter(e -> e.getCode().equals(code))
                    .findFirst().orElse(null);
        }
    }
}
