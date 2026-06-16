package com.baseline.common.service;

import com.baseline.common.dto.SysTenantDetailBizDTO;
import com.baseline.common.dto.SysTenantFilterBizDTO;
import com.baseline.common.dto.TenantHierarchyBizDTO;
import com.baseline.common.vo.SysTenantBizVO;
import com.baseline.common.vo.SysTenantTreeBizVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 租户业务服务接口
 *
 * @author cascade
 * @date 2025/11/17
 */
@Service
public interface ISysTenantBizService {

    /**
     * 获取租户的所有子租户ID（包括自己）
     * 用于层级租户数据权限控制
     *
     * @param dto 租户层级查询条件
     * @param source 来源标识
     * @return 租户及其所有子租户ID列表
     */
    List<Long> getTenantAndChildrenIds(TenantHierarchyBizDTO dto, String source);

    /**
     * 获取租户列表
     *
     * @param dto 查询条件
     * @param source 来源标识
     * @return 租户列表
     */
    List<SysTenantBizVO> getTenantList(SysTenantFilterBizDTO dto, String source);

    /**
     * 获取租户树形结构
     *
     * @param dto 查询条件
     * @param source 来源标识
     * @return 租户树形结构列表
     */
    List<SysTenantTreeBizVO> getTenantTree(SysTenantFilterBizDTO dto, String source);

    /**
     * 获取租户详情
     * 支持通过ID或编码查询
     *
     * @param dto 查询条件
     * @param source 来源标识
     * @return 租户详情
     */
    SysTenantBizVO getTenantDetail(SysTenantDetailBizDTO dto, String source);
}
