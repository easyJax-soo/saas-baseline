package com.baseline.common.annotation.serializer;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.baseline.common.constant.CommonConstants;
import com.baseline.common.dto.DictBizDTO;
import com.baseline.common.service.ISysDictBizService;
import com.baseline.common.annotation.Dict;
import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.vo.DictBizVO;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DictSerializer extends StdSerializer<Object> implements ContextualSerializer {

    private String dictType;
    private String targetField;
    private String defaultText;
    
    // 字典缓存：dictType -> Map<value, label>
    private static final ConcurrentHashMap<String, Map<String, String>> DICT_CACHE = new ConcurrentHashMap<>();
    
    // 缓存过期时间（毫秒），默认30 秒
    private static final long CACHE_EXPIRE_TIME = 30 * 1000;
    
    // 缓存时间戳：dictType -> timestamp
    private static final ConcurrentHashMap<String, Long> CACHE_TIMESTAMP = new ConcurrentHashMap<>();

    public DictSerializer() {
        super(Object.class);
    }

    public DictSerializer(String dictType, String targetField, String defaultText) {
        super(Object.class);
        this.dictType = dictType;
        this.targetField = targetField;
        this.defaultText = defaultText;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        // 判断是否为 List 类型
        if (value instanceof List) {
            serializeList((List<?>) value, gen);
        } else {
            serializeSingle(value, gen);
        }
    }
    
    /**
     * 序列化单个值
     */
    private void serializeSingle(Object value, JsonGenerator gen) throws IOException {
        // 1. 将字段值序列化为字符串（无论原始类型是 Integer 还是 String）
        String valueStr = value != null ? String.valueOf(value) : "";
        gen.writeString(valueStr);

        // 2. 获取字典映射（使用缓存）
        Map<String, String> dictMap = getDictMapWithCache(dictType);

        // 3. 获取字典对应的标签文本
        String dictText = dictMap.get(valueStr);
        if (dictText == null) {
            dictText = defaultText != null ? defaultText : CommonConstants.DICT_DEFAULT_TEXT;
        }

        // 4. 将转换后的文本写入一个额外的字段
        gen.writeFieldName(targetField);
        gen.writeString(dictText);
    }
    
    /**
     * 序列化 List 类型的值
     */
    private void serializeList(List<?> valueList, JsonGenerator gen) throws IOException {
        // 1. 序列化原始值列表
        gen.writeStartArray();
        for (Object item : valueList) {
            String valueStr = item != null ? String.valueOf(item) : "";
            gen.writeString(valueStr);
        }
        gen.writeEndArray();
        
        // 2. 获取字典映射（使用缓存）
        Map<String, String> dictMap = getDictMapWithCache(dictType);
        
        // 3. 转换为字典文本列表
        List<String> textList = new ArrayList<>();
        for (Object item : valueList) {
            String valueStr = item != null ? String.valueOf(item) : "";
            String dictText = dictMap.get(valueStr);
            if (dictText == null) {
                dictText = defaultText != null ? defaultText : CommonConstants.DICT_DEFAULT_TEXT;
            }
            textList.add(dictText);
        }
        
        // 4. 将转换后的文本列表写入一个额外的字段
        gen.writeFieldName(targetField);
        gen.writeStartArray();
        for (String text : textList) {
            gen.writeString(text);
        }
        gen.writeEndArray();
    }
    
    /**
     * 获取字典映射（带缓存）
     */
    private Map<String, String> getDictMapWithCache(String dictType) {
        long currentTime = System.currentTimeMillis();
        Long cacheTime = CACHE_TIMESTAMP.get(dictType);
        
        // 检查缓存是否存在且未过期
        if (cacheTime != null && (currentTime - cacheTime) < CACHE_EXPIRE_TIME) {
            Map<String, String> cachedMap = DICT_CACHE.get(dictType);
            if (cachedMap != null) {
                return cachedMap;
            }
        }
        
        // 缓存不存在或已过期，重新查询
        ISysDictBizService sysDictBizService = SpringUtil.getBean(ISysDictBizService.class);
        DictBizDTO dto = new DictBizDTO();
        dto.setCode(dictType);
        List<DictBizVO> dictList = sysDictBizService.getDictDataListByCode(dto, SecurityConstants.INNER);
        Map<String, String> dictMap = dictList.stream()
                .collect(Collectors.toMap(DictBizVO::getValue, DictBizVO::getLabel, (key1, key2) -> key2));
        
        // 更新缓存
        DICT_CACHE.put(dictType, dictMap);
        CACHE_TIMESTAMP.put(dictType, currentTime);
        
        return dictMap;
    }
    
    /**
     * 清除指定类型的字典缓存
     */
    public static void clearDictCache(String dictType) {
        DICT_CACHE.remove(dictType);
        CACHE_TIMESTAMP.remove(dictType);
    }
    
    /**
     * 清除所有字典缓存
     */
    public static void clearAllDictCache() {
        DICT_CACHE.clear();
        CACHE_TIMESTAMP.clear();
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        // 获取字段上的@Dict注解
        Dict dictAnnotation = property.getAnnotation(Dict.class);
        if (dictAnnotation != null) {
            String targetFieldName = dictAnnotation.targetField();
            if (targetFieldName.isEmpty()) {
                // 默认目标字段名：原字段名 + "Text"
                targetFieldName = property.getName() + "Text";
            }
            // 创建并返回一个配置好的序列化器实例
            return new DictSerializer(dictAnnotation.dictType(), targetFieldName, dictAnnotation.defaultText());
        }
        return this;
    }
}