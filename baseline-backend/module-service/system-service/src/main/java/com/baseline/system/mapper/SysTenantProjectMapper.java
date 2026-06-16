package com.baseline.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baseline.system.entity.SysTenantProject;
import com.baseline.system.entity.SysProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 租户项目表 Mapper 接口
 *
 * @author system
 */
@Mapper
public interface SysTenantProjectMapper extends BaseMapper<SysTenantProject> {

    /**
     * 获取租户关联的项目列表（用于下拉选项）
     * 注意：多租户拦截器会自动添加tenant_id条件
     *
     * @return 项目列表
     */
    List<SysProject> selectTenantProjects();
}
