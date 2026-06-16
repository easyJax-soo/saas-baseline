package com.baseline.redis.config;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

public class PrefixedStringRedisSerializer extends StringRedisSerializer {

    private final String prefix;

    public PrefixedStringRedisSerializer(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public byte[] serialize(String string) {
        if (StringUtils.hasText(prefix)) {
            string = prefix + string;  // 给 key 添加前缀
        }
        return super.serialize(string);
    }

    @Override
    public String deserialize(byte[] bytes) {
        String result = super.deserialize(bytes);
        if (StringUtils.hasText(result) && StringUtils.hasText(prefix) && result.startsWith(prefix)) {
            return result.substring(prefix.length());  // 移除前缀
        }
        return result;
    }
}
