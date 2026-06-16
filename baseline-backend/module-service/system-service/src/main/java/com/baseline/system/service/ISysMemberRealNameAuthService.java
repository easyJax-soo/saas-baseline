package com.baseline.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.dto.SysMemberRealNameAuthAuditDTO;
import com.baseline.system.entity.SysMemberRealNameAuth;

/**
 * <p>
 * 会员实名认证信息表 服务类
 * </p>
 *
 * @author system
 * @since 2024-10-04
 */
public interface ISysMemberRealNameAuthService extends IService<SysMemberRealNameAuth> {

    /**
     * 审核实名认证
     *
     * @param dto 审核信息
     * @return 是否成功
     */
    boolean auditAuth(SysMemberRealNameAuthAuditDTO dto);

    /**
     * 根据会员ID查询认证信息
     *
     * @param memberId 会员ID
     * @return 认证信息
     */
    SysMemberRealNameAuth getByMemberId(Long memberId);

    /**
     * 检查证件号码是否已使用
     *
     * @param certNo 证件号码
     * @param memberId 会员ID（排除自己）
     * @return 是否已使用
     */
    boolean checkCertNoExists(String certNo, Long memberId);


    /**
     * 批量获取会员认证状态
     *
     * @param memberIds 会员ID列表
     * @return 会员ID到认证状态的映射
     */
    java.util.Map<Long, Integer> batchGetMemberAuthStatus(java.util.List<Long> memberIds);
}
