package com.baseline.core.annotation;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.baseline.core.annotation.serializer.EnumeratorSerializer;

import java.util.Arrays;

//@JsonDeserialize(using = EnumeratorDeserializer.class)
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = EnumeratorSerializer.class) // 应用自定义序列化器
public interface IEnumerator {
    Integer getCode();
    String getDescription();

    static <E extends Enum<E> & IEnumerator> E codeOf(Integer code, Class<E> clazz){

        return Arrays.stream(clazz.getEnumConstants())
                .filter(e -> e.getCode().equals(code))
                .findFirst().orElse(null);
    }


}
