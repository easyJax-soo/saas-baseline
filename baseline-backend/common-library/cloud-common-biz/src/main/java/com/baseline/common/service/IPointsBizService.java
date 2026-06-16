package com.baseline.common.service;

import com.baseline.common.dto.HandlePointsDTO;
import com.baseline.common.dto.PointOrganizeUserBindBIzDTO;
import com.baseline.common.dto.SysUserFilterBizDTO;
import com.baseline.common.vo.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 积分服务业务接口
 *
 * @author cascade
 * @date 2025/11/15
 */
@Service
public interface IPointsBizService {

    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @param source 来源标识
     * @return 用户信息
     */
    SysUserSaveVO getUserDetail(Long id, String source);

    /**
     * 获取用户简单列表
     *
     * @param dto 查询条件
     * @param source 来源标识
     * @return 用户列表
     */
    List<SysUserVO> getSimpleList(SysUserFilterBizDTO dto, String source);

    boolean organizeBind(PointOrganizeUserBindBIzDTO dto, String source);

    PointVillageOrganizationBizVO organizeInfo(String source);

    /**
     * 扣减积分
     *
     * @param dto 扣减积分DTO
     * @param source 来源标识
     * @return 操作结果
     */
    Boolean deductPoints(HandlePointsDTO dto, String source);

    /**
     * 积分排行榜
     *
     * @param source 来源标识
     * @return 排行榜列表
     */
    List<SystemUserPointsTopVO> pointsTop(String source);

    /**
     * 获取用户积分
     *
     * @param id 用户ID
     * @param source 来源标识
     * @return 用户积分信息
     */
    SystemUserPointsVO userPoints(Long id, String source);
}
