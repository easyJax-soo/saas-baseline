package com.baseline.common.annotation;


import com.baseline.common.annotation.serializer.DictValidatorSerializer;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = DictValidatorSerializer.class) // 指定校验器
public @interface DictVaild {
    /**
    * 字典类型 (例如 "user_status")
    */
    String dictType();
    
    /**
    * 错误消息
    */
    String message() default "字典不在范围内";
    
    /**
    * 指定验证分组
    */
    Class<?>[] groups() default {};

    /**
    * 负载
    */
    Class<? extends Payload>[] payload() default {};
}