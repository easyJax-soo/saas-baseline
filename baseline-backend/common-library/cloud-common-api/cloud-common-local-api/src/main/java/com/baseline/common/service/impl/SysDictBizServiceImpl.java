package com.baseline.common.service.impl;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.DictBizDTO;
import com.baseline.common.service.ISysDictBizService;
import com.baseline.common.vo.DictBizVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 系统字典业务服务本地实现
 * 用于单体模式下的本地调用
 */
@Service
public class SysDictBizServiceImpl implements ISysDictBizService {

    @Resource(name = "sysDictDataServiceImpl")
    private Object sysDictDataService;

    @Override
    public List<DictBizVO> getDictDataListByCode(DictBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        try {
            // 使用反射调用方法 - 注意方法名是 getDictDataByDictCode
            java.lang.reflect.Method method = sysDictDataService.getClass().getMethod("getDictDataByDictCode", DictBizDTO.class);
            Object result = method.invoke(sysDictDataService, dto);
            
            // 处理可能的类型转换问题
            if (result instanceof List) {
                @SuppressWarnings("unchecked")
                List<Object> objList = (List<Object>) result;

                // 检查列表中的元素类型
                if (!objList.isEmpty() && objList.get(0) instanceof com.alibaba.fastjson2.JSONObject) {
                    // 如果是JSONObject，需要转换为DictBizVO
                    java.util.List<DictBizVO> convertedList = new java.util.ArrayList<>();
                    for (Object obj : objList) {
                        if (obj instanceof com.alibaba.fastjson2.JSONObject) {
                            com.alibaba.fastjson2.JSONObject jsonObj = (com.alibaba.fastjson2.JSONObject) obj;
                            DictBizVO dictVO = jsonObj.toJavaObject(DictBizVO.class);
                            convertedList.add(dictVO);
                        }
                    }
                    return convertedList;
                } else {
                    // 如果已经是正确类型，直接返回
                    @SuppressWarnings("unchecked")
                    List<DictBizVO> dictList = (List<DictBizVO>) result;
                    return dictList;
                }
            }
            
            return new java.util.ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("调用系统字典服务失败", e);
        }
    }
}
