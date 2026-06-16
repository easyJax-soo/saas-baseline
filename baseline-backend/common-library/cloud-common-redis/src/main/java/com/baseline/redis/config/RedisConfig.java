package com.baseline.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ComponentScan("com.baseline.redis")
public class RedisConfig {

    @Value("${spring.redis.cachePrefix:hc::}")
    public String cachePrefix;

    @Bean
    @SuppressWarnings(value = { "unchecked", "rawtypes" })
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        FastJson2JsonRedisSerializer serializer = new FastJson2JsonRedisSerializer(Object.class);

        // 使用自定义的Key序列化器，添加统一前缀
        template.setKeySerializer(new PrefixedStringRedisSerializer(cachePrefix));
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new PrefixedStringRedisSerializer(cachePrefix));
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }


}
