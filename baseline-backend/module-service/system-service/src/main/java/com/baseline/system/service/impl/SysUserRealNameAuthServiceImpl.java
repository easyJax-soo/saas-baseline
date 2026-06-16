package com.baseline.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.common.constant.CommonConstants;
import com.baseline.core.exception.BusinessException;
import com.baseline.system.dto.SysUserRealNameAuthAuditDTO;
import com.baseline.system.entity.SysUserRealNameAuth;
import com.baseline.system.enums.RealNameAuthStatusEnum;
import com.baseline.system.enums.RealNameAuthTypeEnum;
import com.baseline.system.mapper.SysUserRealNameAuthMapper;
import com.baseline.system.service.ISysUserRealNameAuthService;
import com.baseline.system.vo.SysUserRealNameAuthVO;
import com.baseline.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户实名认证信息表 服务实现类
 * </p>
 *
 * @author system
 * @since 2024-01-01
 */
@Service
public class SysUserRealNameAuthServiceImpl extends ServiceImpl<SysUserRealNameAuthMapper, SysUserRealNameAuth> implements ISysUserRealNameAuthService {


    @Override
    public SysUserRealNameAuthVO getUserAuth(Long userId) {
        SysUserRealNameAuth auth = this.lambdaQuery()
                .eq(SysUserRealNameAuth::getUserId, userId)
                .eq(SysUserRealNameAuth::getDeleted, CommonConstants.SYS_NOT_DELETED)
                .one();
        
        if (auth == null) {
            return null;
        }
        
        return convertToVO(auth);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean auditAuth(SysUserRealNameAuthAuditDTO auditDTO) {
        SysUserRealNameAuth auth = this.getById(auditDTO.getId());
        if (auth == null || CommonConstants.SYS_DELETED.equals(auth.getDeleted())) {
            throw new BusinessException("认证记录不存在");
        }
        
        if (!RealNameAuthStatusEnum.PENDING.getCode().equals(auth.getAuthStatus())) {
            throw new BusinessException("只能审核待审核状态的记录");
        }
        
        Long currentUserId = SecurityUtils.getUserId();
        String currentUserName = SecurityUtils.getUsername();
        
        // 更新审核信息
        auth.setAuthStatus(auditDTO.getAuthStatus());
        auth.setAuditTime(LocalDateTime.now());
        auth.setAuditorId(currentUserId);
        auth.setAuditorName(currentUserName);
        auth.setAuditRemark(auditDTO.getAuditRemark());
        
        // 如果审核通过，设置过期时间（例如：3年后过期）
        if (RealNameAuthStatusEnum.APPROVED.getCode().equals(auditDTO.getAuthStatus())) {
            auth.setExpireTime(LocalDateTime.now().plusYears(3));
        }
        
        return this.updateById(auth);
    }


    @Override
    public boolean isCertNoUsed(String certNo, Long excludeUserId) {
        String certNoHash = DigestUtil.sha256Hex(certNo);
        
        LambdaQueryWrapper<SysUserRealNameAuth> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRealNameAuth::getCertNoHash, certNoHash)
                .eq(SysUserRealNameAuth::getDeleted, CommonConstants.SYS_NOT_DELETED);
        
        if (excludeUserId != null) {
            queryWrapper.ne(SysUserRealNameAuth::getUserId, excludeUserId);
        }
        
        return this.exists(queryWrapper);
    }

    /**
     * 转换为VO对象
     */
    private SysUserRealNameAuthVO convertToVO(SysUserRealNameAuth auth) {
        SysUserRealNameAuthVO vo = new SysUserRealNameAuthVO();
        BeanUtil.copyProperties(auth, vo);
        
        // 设置枚举描述
        RealNameAuthTypeEnum typeEnum = RealNameAuthTypeEnum.getByCode(auth.getAuthType());
        if (typeEnum != null) {
            vo.setAuthTypeName(typeEnum.getDesc());
        }
        
        RealNameAuthStatusEnum statusEnum = RealNameAuthStatusEnum.getByCode(auth.getAuthStatus());
        if (statusEnum != null) {
            vo.setAuthStatusName(statusEnum.getDesc());
        }
        
        // 性别描述
        if (auth.getGender() != null) {
            vo.setGenderName(auth.getGender() == 1 ? "男" : "女");
        }
        
        // 证件号码脱敏
        if (StrUtil.isNotBlank(auth.getCertNo())) {
            vo.setCertNoMasked(maskCertNo(auth.getCertNo()));
        }
        
        return vo;
    }

    /**
     * 证件号码脱敏
     */
    private String maskCertNo(String certNo) {
        if (StrUtil.isBlank(certNo) || certNo.length() < 8) {
            return certNo;
        }
        
        int length = certNo.length();
        if (length <= 8) {
            return certNo.substring(0, 2) + "****" + certNo.substring(length - 2);
        } else {
            return certNo.substring(0, 4) + "****" + certNo.substring(length - 4);
        }
    }

    @Override
    public Map<Long, Integer> batchGetUserAuthStatus(List<Long> userIds) {
        if (CollectionUtil.isEmpty(userIds)) {
            return new HashMap<>();
        }
        
        // 查询用户的实名认证记录
        List<SysUserRealNameAuth> authList = this.lambdaQuery()
                .in(SysUserRealNameAuth::getUserId, userIds)
                .eq(SysUserRealNameAuth::getDeleted, CommonConstants.SYS_NOT_DELETED)
                .list();
        
        // 构建用户ID到认证状态的映射
        Map<Long, Integer> userAuthStatusMap = new HashMap<>();
        for (SysUserRealNameAuth auth : authList) {
            // 检查是否过期（只有审核通过的记录才检查过期）
            Integer status = auth.getAuthStatus();
            if (RealNameAuthStatusEnum.APPROVED.getCode().equals(status) 
                && auth.getExpireTime() != null 
                && auth.getExpireTime().isBefore(LocalDateTime.now())) {
                status = RealNameAuthStatusEnum.EXPIRED.getCode();
            }
            userAuthStatusMap.put(auth.getUserId(), status);
        }
        
        return userAuthStatusMap;
    }
}