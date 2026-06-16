package com.baseline.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baseline.core.annotation.IEnumerator;

import java.util.Arrays;


public class SysRoleEnum {
    public enum Status implements IEnumerator {
        ENABLE(1,"正常"),DISABLE(0,"禁用");
        @EnumValue
        private final Integer code;
        private final String description;
        Status(Integer code,String description){
            this.code=code;
            this.description=description;
        }
        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String getDescription() {
            return this.description;
        }
        public static Status codeOf(Integer code){
            return Arrays.stream(Status.values())
                        .filter(e -> e.getCode().equals(code))
                        .findFirst().orElse(null);
        }
    }

    public enum DataScope implements IEnumerator {
        ALL(1,"全部数据权限"),CUSTOM(2,"自定数据权限"),DEPT(3,"部门数据权限"),DEPT_AND_CHILD(4,"部门及以下数据权限"),SELF(5,"仅本人数据权限");

        @EnumValue
        private final Integer code;
        private final String description;
        DataScope(Integer code,String description){
            this.code=code;
            this.description=description;
        }
        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String getDescription() {
            return this.description;
        }
        public static DataScope codeOf(Integer code){
            return Arrays.stream(DataScope.values())
                    .filter(e -> e.getCode().equals(code))
                    .findFirst().orElse(null);
        }
    }
}

