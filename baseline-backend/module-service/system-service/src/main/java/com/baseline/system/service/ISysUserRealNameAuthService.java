package com.baseline.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.dto.SysUserRealNameAuthAuditDTO;
import com.baseline.system.entity.SysUserRealNameAuth;
import com.baseline.system.vo.SysUserRealNameAuthVO;

import java.util.Map;

/**
 * <p>
 * 用户实名认证信息表 服务类
 * </p>
 *
 * @author system
 * @since 2024-01-01
 */
public interface ISysUserRealNameAuthService extends IService<SysUserRealNameAuth> {

    /**
     * 获取用户的实名认证信息
     * @param userId 用户ID
     * @return 认证信息
     */
    SysUserRealNameAuthVO getUserAuth(Long userId);

    /**
     * 审核实名认证
     * @param auditDTO 审核信息
     * @return 是否成功
     */
    boolean auditAuth(SysUserRealNameAuthAuditDTO auditDTO);

    /**
     * 检查证件号码是否已被使用
     * @param certNo 证件号码
     * @param excludeUserId 排除的用户ID（用于更新时排除自己）
     * @return 是否已被使用
     */
    boolean isCertNoUsed(String certNo, Long excludeUserId);

    /**
     * 批量获取用户实名认证状态
     * @param userIds 用户ID列表
     * @return 用户ID到认证状态的映射，key为用户ID，value为认证状态（0-待审核，1-已认证，2-已拒绝，3-已过期）
     */
    Map<Long, Integer> batchGetUserAuthStatus(java.util.List<Long> userIds);
}
