package com.baseline.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.core.exception.BusinessException;
import com.baseline.system.dto.SysTenantUserDetailDTO;
import com.baseline.system.dto.SysTenantUserFilterDTO;
import com.baseline.system.dto.SysTenantUserBindDTO;
import com.baseline.system.dto.SysTenantUserUnbindDTO;
import com.baseline.system.dto.SysTenantUnboundUserFilterDTO;
import com.baseline.system.entity.SysTenantUser;
import com.baseline.system.entity.SysUser;
import com.baseline.system.mapper.SysTenantUserMapper;
import com.baseline.system.service.ISysTenantUserService;
import com.baseline.system.service.ISysUserService;
import com.baseline.system.vo.SysTenantUserDetailVO;
import com.baseline.utils.cache.PermissionCacheUtils;
import com.baseline.system.vo.SysTenantUnboundUserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 租户信息表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Service
public class SysTenantUserServiceImpl extends ServiceImpl<SysTenantUserMapper, SysTenantUser> implements ISysTenantUserService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    public SysTenantUserDetailVO detail(SysTenantUserDetailDTO dto) {
        return baseMapper.getDetailById(dto.getId());
    }

    @Override
    @Transactional
    public boolean remove(List<Long> ids) {
        // 先获取要删除的租户用户关联信息，用于清除缓存
        List<SysTenantUser> tenantUsers = baseMapper.selectBatchIds(ids);
        List<Long> userIds = tenantUsers.stream()
                .map(SysTenantUser::getUserId)
                .collect(Collectors.toList());
        
        // 删除租户用户关联关系
        int res = baseMapper.deleteBatchIds(ids);
        
        // 清除相关用户的缓存
        if (res > 0 && !userIds.isEmpty()) {
            PermissionCacheUtils.clearUserCacheBatch(userIds);
        }
        
        return res > 0;
    }

    @Override
    public IPage<SysTenantUserDetailVO> pageTenantUser(SysTenantUserFilterDTO dto) {
        Page<SysTenantUserDetailVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return baseMapper.pageTenantUser(page, dto);
    }

    @Override
    @Transactional
    public boolean bindUserToTenant(SysTenantUserBindDTO dto) {
        // 检查用户是否存在
        SysUser user = sysUserService.getById(dto.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 检查是否已经绑定
        SysTenantUser existingBind = lambdaQuery()
                .eq(SysTenantUser::getTenantId, dto.getTenantId())
                .eq(SysTenantUser::getUserId, dto.getUserId())
                .one();
        
        if (existingBind != null) {
            throw new BusinessException("用户已绑定到该租户");
        }

        // 创建租户用户关联
        SysTenantUser tenantUser = new SysTenantUser();
        tenantUser.setTenantId(dto.getTenantId());
        tenantUser.setUserId(dto.getUserId());
        tenantUser.setIsTenantAdmin(dto.getIsTenantAdmin());
        tenantUser.setStatus(1); // 正常状态
        tenantUser.setCreateTime(LocalDateTime.now());
        tenantUser.setUpdateTime(LocalDateTime.now());

        return save(tenantUser);
    }

    @Override
    @Transactional
    public boolean unbindTenantUsers(SysTenantUserUnbindDTO dto) {
        if (dto.getTenantId() == null) {
            throw new BusinessException("租户ID不能为空");
        }
        if (dto.getUserIds() == null || dto.getUserIds().isEmpty()) {
            throw new BusinessException("用户ID列表不能为空");
        }

        // 根据租户ID和用户ID列表查找对应的租户用户关联记录
        List<SysTenantUser> tenantUsers = lambdaQuery()
                .eq(SysTenantUser::getTenantId, dto.getTenantId())
                .in(SysTenantUser::getUserId, dto.getUserIds())
                .list();

        if (tenantUsers.isEmpty()) {
            throw new BusinessException("未找到对应的租户用户关联记录");
        }

        // 提取关联ID进行删除
        List<Long> ids = tenantUsers.stream()
                .map(SysTenantUser::getId)
                .collect(Collectors.toList());

        return remove(ids);
    }

    @Override
    public IPage<SysTenantUnboundUserVO> getUnboundUsers(SysTenantUnboundUserFilterDTO dto) {
        if (dto.getTenantId() == null) {
            throw new BusinessException("租户ID不能为空");
        }
        
        Page<SysTenantUnboundUserVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return baseMapper.getUnboundUsers(page, dto);
    }
}
