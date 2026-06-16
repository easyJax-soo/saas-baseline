package com.baseline.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.dto.*;
import com.baseline.system.entity.SysTenantPermission;

import java.util.List;

/**
 * <p>
 * 租户权限表 服务类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface ISysTenantPermissionService extends IService<SysTenantPermission> {


    /**
     * 保存
     * @param dto
     * @return
     */
    boolean saveOrUpdate(SysTenantPermissionSaveDTO dto);

    /**
     * 获取租户权限ID列表
     * @param tenantId
     * @return
     */
    List<Long> getDetailByTenantId(Long tenantId);
}
