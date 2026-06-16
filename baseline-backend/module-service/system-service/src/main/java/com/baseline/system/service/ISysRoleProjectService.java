package com.baseline.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.entity.SysRoleProject;

import java.util.List;

/**
 * 角色和项目关联表 服务类
 *
 * @author system
 */
public interface ISysRoleProjectService extends IService<SysRoleProject> {

    /**
     * 根据角色ID获取项目编码列表
     * @param roleId 角色ID
     * @return 项目编码列表
     */
    List<String> getProjectCodesByRoleId(Long roleId);

    /**
     * 为角色分配项目
     * @param roleId 角色ID
     * @param projectCodes 项目编码列表
     * @return 是否成功
     */
    boolean assignProjectsToRole(Long roleId, List<String> projectCodes);
}
