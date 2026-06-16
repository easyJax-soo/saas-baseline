package com.baseline.common.annotation.serializer;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baseline.common.annotation.DictVaild;
import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.DictBizDTO;
import com.baseline.common.service.ISysDictBizService;
import com.baseline.common.vo.DictBizVO;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DictValidatorSerializer implements ConstraintValidator<DictVaild, Object> {

    private String dictType;

    @Override
    public void initialize(DictVaild constraintAnnotation) {
        this.dictType = constraintAnnotation.dictType();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 如果值为空，则认为有效（空值验证应该由 @NotNull 等注解处理）
        if (ObjectUtil.isNull(value)) {
            return true;
        }
        
        // 获取字典数据
        ISysDictBizService sysDictBizService = SpringUtil.getBean(ISysDictBizService.class);
        DictBizDTO dto = new DictBizDTO();
        dto.setCode(dictType);
        List<DictBizVO> dictList = sysDictBizService.getDictDataListByCode(dto, SecurityConstants.INNER);
        Map<String, String> dictMap = dictList.stream().collect(Collectors.toMap(DictBizVO::getValue, DictBizVO::getLabel, (key1, key2) -> key2));
        Set<String> validValues = dictMap.keySet();

        // 判断是否为 List 类型
        if (value instanceof List) {
            return isValidList((List<?>) value, validValues, context);
        } else {
            return isValidSingle(value, validValues, context);
        }
    }
    
    /**
     * 校验单个值
     */
    private boolean isValidSingle(Object value, Set<String> validValues, ConstraintValidatorContext context) {
        // 将输入值转换为字符串进行比较（因为字典存储的值都是字符串）
        String valueStr = String.valueOf(value);
        
        if (!validValues.contains(valueStr)) {
            // 禁用默认的错误消息
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                "值 '" + value + "' 不在字典类型 '" + dictType + "' 的有效范围内。有效值为: " + validValues
            ).addConstraintViolation(); 
            return false;
        }
        return true;
    }
    
    /**
     * 校验 List 类型的值
     */
    private boolean isValidList(List<?> valueList, Set<String> validValues, ConstraintValidatorContext context) {
        // 检查列表中的每个值是否都在有效范围内
        for (Object item : valueList) {
            if (item == null) {
                continue; // 跳过 null 值
            }
            String valueStr = String.valueOf(item);
            if (!validValues.contains(valueStr)) {
                // 禁用默认的错误消息
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                    "列表中的值 '" + item + "' 不在字典类型 '" + dictType + "' 的有效范围内。有效值为: " + validValues
                ).addConstraintViolation(); 
                return false;
            }
        }
        return true;
    }
}