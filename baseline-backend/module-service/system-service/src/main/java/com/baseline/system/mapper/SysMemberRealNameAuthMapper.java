package com.baseline.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baseline.system.entity.SysMemberRealNameAuth;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员实名认证信息表 Mapper 接口
 * </p>
 *
 * @author system
 * @since 2024-10-04
 */
public interface SysMemberRealNameAuthMapper extends BaseMapper<SysMemberRealNameAuth> {

    /**
     * 根据会员ID查询认证信息
     *
     * @param memberId 会员ID
     * @return 认证信息
     */
    SysMemberRealNameAuth selectByMemberId(@Param("memberId") Long memberId);

    /**
     * 根据证件号码哈希值查询认证信息
     *
     * @param certNoHash 证件号码哈希值
     * @return 认证信息
     */
    SysMemberRealNameAuth selectByCertNoHash(@Param("certNoHash") String certNoHash);

    /**
     * 检查会员是否已实名认证
     *
     * @param memberId 会员ID
     * @return 是否已认证
     */
    boolean checkMemberAuth(@Param("memberId") Long memberId);
}
