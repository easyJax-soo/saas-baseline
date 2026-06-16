package com.baseline.common.service.impl;

import com.baseline.common.dto.SysDeptFilterBizDTO;
import com.baseline.common.service.ISysDeptBizService;
import com.baseline.common.vo.SysDeptVO;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 部门业务服务本地实现
 * 用于单体模式下的本地调用
 */
@Service
public class SysDeptBizServiceImpl implements ISysDeptBizService {

    @Resource(name = "sysDeptServiceImpl")
    private Object sysDeptService;

    @Override
    @SuppressWarnings("unchecked")
    public List<SysDeptVO> getDeptVOList(SysDeptFilterBizDTO dto, String source) {
        try {
            java.lang.reflect.Method method = sysDeptService.getClass().getMethod("getDeptVOList", SysDeptFilterBizDTO.class);
            return (List<SysDeptVO>) method.invoke(sysDeptService, dto);
        } catch (Exception e) {
            throw new RuntimeException("调用部门列表服务失败", e);
        }
    }
}
