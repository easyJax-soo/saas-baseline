package com.baseline.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.core.exception.BusinessException;
import com.baseline.system.dto.SysMemberRealNameAuthAuditDTO;
import com.baseline.system.entity.SysMemberRealNameAuth;
import com.baseline.system.enums.RealNameAuthStatusEnum;
import com.baseline.system.mapper.SysMemberRealNameAuthMapper;
import com.baseline.system.service.ISysMemberRealNameAuthService;
import com.baseline.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员实名认证信息表 服务实现类
 * </p>
 *
 * @author system
 * @since 2024-10-04
 */
@Service
public class SysMemberRealNameAuthServiceImpl extends ServiceImpl<SysMemberRealNameAuthMapper, SysMemberRealNameAuth> implements ISysMemberRealNameAuthService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean auditAuth(SysMemberRealNameAuthAuditDTO dto) {
        SysMemberRealNameAuth auth = getById(dto.getId());
        if (ObjectUtil.isNull(auth)) {
            throw new BusinessException("认证记录不存在");
        }

        if (auth.getAuthStatus() != 0) {
            throw new BusinessException("只能审核待审核状态的记录");
        }

        Long currentUserId = SecurityUtils.getUserId();
        String currentUserName = SecurityUtils.getUsername();

        auth.setAuthStatus(dto.getAuthStatus());
        auth.setAuditTime(LocalDateTime.now());
        auth.setAuditorId(currentUserId);
        auth.setAuditorName(currentUserName);
        auth.setAuditRemark(dto.getAuditRemark());

        // 如果审核通过，设置过期时间（假设3年有效期）
        if (dto.getAuthStatus() == 1) {
            auth.setExpireTime(LocalDateTime.now().plusYears(3));
        }

        return updateById(auth);
    }

    @Override
    public SysMemberRealNameAuth getByMemberId(Long memberId) {
        return baseMapper.selectByMemberId(memberId);
    }

    @Override
    public boolean checkCertNoExists(String certNo, Long memberId) {
        String certNoHash = DigestUtil.md5Hex(certNo);
        SysMemberRealNameAuth existAuth = baseMapper.selectByCertNoHash(certNoHash);
        
        if (ObjectUtil.isNull(existAuth)) {
            return false;
        }

        // 如果是同一个会员，不算重复
        if (ObjectUtil.isNotNull(memberId) && existAuth.getMemberId().equals(memberId)) {
            return false;
        }

        return true;
    }

    @Override
    public Map<Long, Integer> batchGetMemberAuthStatus(List<Long> memberIds) {
        if (CollectionUtil.isEmpty(memberIds)) {
            return new HashMap<>();
        }
        
        // 查询会员的实名认证记录
        List<SysMemberRealNameAuth> authList = this.lambdaQuery()
                .in(SysMemberRealNameAuth::getMemberId, memberIds)
                .eq(SysMemberRealNameAuth::getDeleted, false)
                .list();
        
        // 构建会员ID到认证状态的映射
        Map<Long, Integer> memberAuthStatusMap = new HashMap<>();
        for (SysMemberRealNameAuth auth : authList) {
            // 检查是否过期（只有审核通过的记录才检查过期）
            Integer status = auth.getAuthStatus();
            if (RealNameAuthStatusEnum.APPROVED.getCode().equals(status) 
                && auth.getExpireTime() != null 
                && auth.getExpireTime().isBefore(LocalDateTime.now())) {
                status = RealNameAuthStatusEnum.EXPIRED.getCode();
            }
            memberAuthStatusMap.put(auth.getMemberId(), status);
        }
        
        return memberAuthStatusMap;
    }
}
