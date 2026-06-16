package com.baseline.excel.converter;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.baseline.common.annotation.Dict;
import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.DictBizDTO;
import com.baseline.common.service.ISysDictBizService;
import com.baseline.common.vo.DictBizVO;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 字典转换器 - 用于Excel导入导出时的字典值转换
 * 支持@Dict注解标注的Object字段（支持Integer、String等各种字典值类型）
 * 使用ISysDictBizService获取字典数据，与@Dict和@DictVaild保持一致
 * 
 * @author system
 */
public class DictConverter implements Converter<Object> {

    // 字典缓存：dictType -> Map<value, label>
    private static final ConcurrentHashMap<String, Map<String, String>> DICT_CACHE = new ConcurrentHashMap<>();
    
    // 字典反向缓存：dictType -> Map<label, value>
    private static final ConcurrentHashMap<String, Map<String, String>> DICT_REVERSE_CACHE = new ConcurrentHashMap<>();
    
    // 缓存过期时间（毫秒），默认30秒，与DictSerializer保持一致
    private static final long CACHE_EXPIRE_TIME = 30 * 1000;
    
    // 缓存时间戳：dictType -> timestamp
    private static final ConcurrentHashMap<String, Long> CACHE_TIMESTAMP = new ConcurrentHashMap<>();

    @Override
    public Class<?> supportJavaTypeKey() {
        return Object.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Object> context) throws Exception {
        Object value = context.getValue();
        if (value == null) {
            return new WriteCellData<>("");
        }

        // 获取字段上的@Dict注解
        Field field = context.getContentProperty().getField();
        Dict dictAnnotation = field.getAnnotation(Dict.class);
        
        if (dictAnnotation != null) {
            String dictType = dictAnnotation.dictType();
            // 根据字典类型和值获取字典标签
            String dictLabel = getDictLabel(dictType, String.valueOf(value));
            return new WriteCellData<>(dictLabel != null ? dictLabel : String.valueOf(value));
        }
        
        return new WriteCellData<>(String.valueOf(value));
    }

    @Override
    public Object convertToJavaData(ReadConverterContext<?> context) throws Exception {
        try {
            String stringValue = context.getReadCellData().getStringValue();
            if (stringValue == null || stringValue.trim().isEmpty()) {
                return null;
            }

            // 获取字段上的@Dict注解
            Field field = context.getContentProperty().getField();
            if (field == null) {
                return stringValue.trim();
            }
            
            Dict dictAnnotation = field.getAnnotation(Dict.class);
            
            if (dictAnnotation == null) {
                // 如果没有@Dict注解但使用了DictConverter，根据字段类型返回值
                Class<?> fieldType = field.getType();
                if (fieldType == Integer.class || fieldType == int.class) {
                    try {
                        return Integer.valueOf(stringValue.trim());
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
                return stringValue.trim();
            }
            
            if (dictAnnotation != null) {
                String dictType = dictAnnotation.dictType();

                // 根据字典类型和标签获取字典值
                String dictValue = getDictValue(dictType, stringValue.trim());

                // 根据字段类型返回相应的类型
                Class<?> fieldType = field.getType();
                if (fieldType == Integer.class || fieldType == int.class) {
                    try {
                        Integer result = dictValue != null ? Integer.valueOf(dictValue) : null;
                        return result;
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
                return dictValue;
            }
            
            // 如果没有@Dict注解，根据字段类型返回相应的值
            Class<?> fieldType = field.getType();
            if (fieldType == Integer.class || fieldType == int.class) {
                try {
                    return Integer.valueOf(stringValue.trim());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return stringValue.trim();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 根据字典类型和值获取字典标签
     * 使用ISysDictBizService获取字典数据，与@Dict注解保持一致
     */
    private String getDictLabel(String dictType, String dictValue) {
        try {
            Map<String, String> dictMap = getDictMapWithCache(dictType);
            return dictMap.get(dictValue);
        } catch (Exception e) {
            // 如果获取字典失败，返回原值
            return dictValue;
        }
    }

    /**
     * 根据字典类型和标签获取字典值
     * 使用ISysDictBizService获取字典数据，与@DictVaild注解保持一致
     */
    private String getDictValue(String dictType, String dictLabel) {
        try {
            Map<String, String> reverseDictMap = getReverseDictMapWithCache(dictType);

            if (reverseDictMap != null) {
                String valueStr = reverseDictMap.get(dictLabel);
                if (valueStr != null) {
                    return valueStr;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 如果无法匹配字典，返回原值
        return dictLabel;
    }

    /**
     * 获取字典映射（带缓存）value -> label
     * 与DictSerializer的实现保持一致
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
        try {
            ISysDictBizService sysDictBizService = SpringUtil.getBean(ISysDictBizService.class);
            DictBizDTO dto = new DictBizDTO();
            dto.setCode(dictType);
            List<DictBizVO> dictList = sysDictBizService.getDictDataListByCode(dto, SecurityConstants.INNER);

            if (dictList == null || dictList.isEmpty()) {
                return new ConcurrentHashMap<>();
            }
            
            Map<String, String> dictMap = dictList.stream()
                    .collect(Collectors.toMap(DictBizVO::getValue, DictBizVO::getLabel, (key1, key2) -> key2));
            
            // 同时构建反向映射 label -> value
            Map<String, String> reverseDictMap = dictList.stream()
                    .collect(Collectors.toMap(DictBizVO::getLabel, DictBizVO::getValue, (key1, key2) -> key2));
            

            // 更新缓存
            DICT_CACHE.put(dictType, dictMap);
            DICT_REVERSE_CACHE.put(dictType, reverseDictMap);
            CACHE_TIMESTAMP.put(dictType, currentTime);
            
            return dictMap;
        } catch (Exception e) {
            e.printStackTrace();
            return new ConcurrentHashMap<>();
        }
    }

    /**
     * 获取反向字典映射（带缓存）label -> value
     */
    private Map<String, String> getReverseDictMapWithCache(String dictType) {
        long currentTime = System.currentTimeMillis();
        Long cacheTime = CACHE_TIMESTAMP.get(dictType);
        
        // 检查缓存是否存在且未过期
        if (cacheTime != null && (currentTime - cacheTime) < CACHE_EXPIRE_TIME) {
            Map<String, String> cachedMap = DICT_REVERSE_CACHE.get(dictType);
            if (cachedMap != null) {
                return cachedMap;
            }
        }
        
        // 如果反向缓存不存在，先获取正向缓存（这会同时构建反向缓存）
        getDictMapWithCache(dictType);
        return DICT_REVERSE_CACHE.get(dictType);
    }

    /**
     * 清除指定类型的字典缓存
     */
    public static void clearDictCache(String dictType) {
        DICT_CACHE.remove(dictType);
        DICT_REVERSE_CACHE.remove(dictType);
        CACHE_TIMESTAMP.remove(dictType);
    }
    
    /**
     * 清除所有字典缓存
     */
    public static void clearAllDictCache() {
        DICT_CACHE.clear();
        DICT_REVERSE_CACHE.clear();
        CACHE_TIMESTAMP.clear();
    }
}
