package com.baseline.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.system.entity.SysRoleProject;
import com.baseline.system.mapper.SysRoleProjectMapper;
import com.baseline.system.service.ISysRoleProjectService;
import com.baseline.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色和项目关联表 服务实现类
 *
 * @author system
 */
@Service
public class SysRoleProjectServiceImpl extends ServiceImpl<SysRoleProjectMapper, SysRoleProject> implements ISysRoleProjectService {

    @Override
    public List<String> getProjectCodesByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleProject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleProject::getRoleId, roleId);
        
        List<SysRoleProject> roleProjects = this.list(queryWrapper);
        return roleProjects.stream()
                .map(SysRoleProject::getProjectCode)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignProjectsToRole(Long roleId, List<String> projectCodes) {
        // 先删除角色现有的项目关联
        LambdaQueryWrapper<SysRoleProject> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(SysRoleProject::getRoleId, roleId);
        this.remove(deleteWrapper);
        
        if (CollectionUtil.isEmpty(projectCodes)) {
            return true;
        }
        
        // 添加新的项目关联
        List<SysRoleProject> roleProjects = new ArrayList<>();
        Long tenantId = SecurityUtils.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        
        for (String projectCode : projectCodes) {
            SysRoleProject roleProject = new SysRoleProject();
            roleProject.setRoleId(roleId);
            roleProject.setTenantId(tenantId);
            roleProject.setProjectCode(projectCode);
            roleProject.setCreateTime(LocalDateTime.now());
            roleProjects.add(roleProject);
        }
        
        return this.saveBatch(roleProjects);
    }
}
