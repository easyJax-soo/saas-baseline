package com.baseline.core.annotation.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.baseline.core.annotation.IEnumerator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EnumeratorSerializer extends StdSerializer<IEnumerator> {

    /**
     * 兼容历史响应
     * 后续将会去除枚举的返回的对象，统一改成扁平化返回
     * 历史枚举对象返回格式：{"name":"枚举","status":{"code":1,"description":"枚举描述"}}
     * 新的枚举扁平化返回格式：{"name":"枚举","status":1,"statusText":"枚举描述"}
     *
     */
    private static final List<String> PACKAGES = Arrays.asList(
            "com.baseline.agriculture",
            "com.baseline.manage",
            "com.baseline.points",
            "com.baseline.political",
            "com.baseline.village"
    );

    public EnumeratorSerializer() {
        this(null);
    }

    public EnumeratorSerializer(Class<IEnumerator> t) {
        super(t);
    }

    @Override
    public void serialize(IEnumerator value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        String targetPackage = value.getClass().getName();
        // 检查当前枚举的包名是否在配置的扁平化包列表中
        boolean shouldFlatten = PACKAGES.stream()
                .anyMatch(pkg -> targetPackage != null && targetPackage.startsWith(pkg));

        if(!shouldFlatten){
            // 扁平化返回
            // 对于不存在的枚举，统一返回-9999，
            Integer defaultEnumCode = -9999;
            String defaultEnumText = "枚举类型未找到，请检查";

            Integer enumCode = value.getCode() == null ? defaultEnumCode : value.getCode();
            String enumText = value.getCode() == null ? defaultEnumText : value.getDescription();

            String fieldTextName = gen.getOutputContext().getCurrentName() + "Text";
            gen.writeNumber(enumCode.toString());
            gen.writeStringField(fieldTextName, enumText);
        }else{
            // 历史枚举对象返回
            // 写入一个JSON对象
            gen.writeStartObject();
            // 枚举的名称（例如 "ENABLE"）
            if (value instanceof Enum) {
                gen.writeFieldName("name");
                gen.writeString(((Enum<?>) value).name());
            }
            // 枚举的 code（例如 1）
            gen.writeFieldName("code");
            gen.writeNumber(value.getCode());
            // 枚举的 description（例如 "正常"）
            gen.writeFieldName("description");
            gen.writeString(value.getDescription());
            // 结束JSON对象写入
            gen.writeEndObject();
        }

    }
}