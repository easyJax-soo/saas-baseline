package com.baseline.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.entity.SysTenantProject;

import java.util.List;

/**
 * 租户项目表 服务类
 *
 * @author system
 */
public interface ISysTenantProjectService extends IService<SysTenantProject> {

    /**
     * 根据租户ID获取项目编码列表
     *
     * @param tenantId 租户ID
     * @return 项目编码列表
     */
    List<String> getProjectCodesByTenantId(Long tenantId);

    /**
     * 保存租户项目关联
     *
     * @param tenantId 租户ID
     * @param projectCodes 项目编码列表
     * @return 是否成功
     */
    boolean saveTenantProjects(Long tenantId, List<String> projectCodes);
}
