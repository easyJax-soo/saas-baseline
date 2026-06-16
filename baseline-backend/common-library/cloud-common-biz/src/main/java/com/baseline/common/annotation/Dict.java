package com.baseline.common.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.baseline.common.annotation.serializer.DictSerializer;
import com.baseline.common.constant.CommonConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside // 表明这是一个Jackson组合注解
@JsonSerialize(using = DictSerializer.class) // 指定负责序列化的类
public @interface Dict {
    /**
    * 字典类型 (例如 "user_status")
    */
    String dictType();
    
    /**
    * 转换后文本存入的字段名，默认是原字段名 + "Text"
    */
    String targetField() default "";

    /**
     * 字典不存在时的默认文本
     */
    String defaultText() default CommonConstants.DICT_DEFAULT_TEXT;
}