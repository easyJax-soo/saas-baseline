package com.baseline.common.service.impl;

import com.baseline.common.dto.SysTenantDetailBizDTO;
import com.baseline.common.dto.SysTenantFilterBizDTO;
import com.baseline.common.dto.TenantHierarchyBizDTO;
import com.baseline.common.service.ISysTenantBizService;
import com.baseline.common.vo.SysTenantBizVO;
import com.baseline.common.vo.SysTenantTreeBizVO;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 租户业务服务本地实现（单体模式）
 * 直接调用本地服务，无需通过网络
 *
 * @author cascade
 * @date 2025/11/17
 */
@Service
public class SysTenantBizServiceImpl implements ISysTenantBizService {

    @Resource(name = "sysTenantServiceImpl")
    private Object sysTenantService;

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> getTenantAndChildrenIds(TenantHierarchyBizDTO dto, String source) {
        try {
            java.lang.reflect.Method method = sysTenantService.getClass().getMethod("getTenantAndChildrenIds", Long.class);
            return (List<Long>) method.invoke(sysTenantService, dto.getTenantId());
        } catch (Exception e) {
            throw new RuntimeException("调用租户层级服务失败", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SysTenantBizVO> getTenantList(SysTenantFilterBizDTO dto, String source) {
        try {
            java.lang.reflect.Method method = sysTenantService.getClass().getMethod("getTenantList", SysTenantFilterBizDTO.class);
            return (List<SysTenantBizVO>) method.invoke(sysTenantService, dto);
        } catch (Exception e) {
            throw new RuntimeException("调用租户列表服务失败", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SysTenantTreeBizVO> getTenantTree(SysTenantFilterBizDTO dto, String source) {
        try {
            java.lang.reflect.Method method = sysTenantService.getClass().getMethod("getTenantTree", SysTenantFilterBizDTO.class);
            return (List<SysTenantTreeBizVO>) method.invoke(sysTenantService, dto);
        } catch (Exception e) {
            throw new RuntimeException("调用租户树形结构服务失败", e);
        }
    }

    @Override
    public SysTenantBizVO getTenantDetail(SysTenantDetailBizDTO dto, String source) {
        try {
            java.lang.reflect.Method method = sysTenantService.getClass().getMethod("getTenantDetail", SysTenantDetailBizDTO.class);
            return (SysTenantBizVO) method.invoke(sysTenantService, dto);
        } catch (Exception e) {
            throw new RuntimeException("调用租户详情服务失败", e);
        }
    }
}
