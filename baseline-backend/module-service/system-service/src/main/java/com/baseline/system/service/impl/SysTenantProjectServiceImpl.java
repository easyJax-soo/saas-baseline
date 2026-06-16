package com.baseline.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.system.entity.SysTenantProject;
import com.baseline.system.mapper.SysTenantProjectMapper;
import com.baseline.system.service.ISysTenantProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 租户项目表 服务实现类
 *
 * @author system
 */
@Service
public class SysTenantProjectServiceImpl extends ServiceImpl<SysTenantProjectMapper, SysTenantProject> implements ISysTenantProjectService {

    @Override
    public List<String> getProjectCodesByTenantId(Long tenantId) {
        LambdaQueryWrapper<SysTenantProject> query = new LambdaQueryWrapper<>();
        query.eq(SysTenantProject::getTenantId, tenantId);
        
        List<SysTenantProject> list = baseMapper.selectList(query);
        return list.stream()
                .map(SysTenantProject::getProjectCode)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean saveTenantProjects(Long tenantId, List<String> projectCodes) {
        // 先删除该租户的所有项目关联
        LambdaQueryWrapper<SysTenantProject> deleteQuery = new LambdaQueryWrapper<>();
        deleteQuery.eq(SysTenantProject::getTenantId, tenantId);
        baseMapper.delete(deleteQuery);
        
        // 如果项目编码列表为空，则只删除不新增
        if (projectCodes == null || projectCodes.isEmpty()) {
            return true;
        }
        
        // 批量插入新的关联关系
        List<SysTenantProject> tenantProjects = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (String projectCode : projectCodes) {
            SysTenantProject tenantProject = new SysTenantProject();
            tenantProject.setTenantId(tenantId);
            tenantProject.setProjectCode(projectCode);
            tenantProject.setCreateTime(now);
            tenantProject.setUpdateTime(now);
            tenantProjects.add(tenantProject);
        }
        
        return saveBatch(tenantProjects);
    }
}
