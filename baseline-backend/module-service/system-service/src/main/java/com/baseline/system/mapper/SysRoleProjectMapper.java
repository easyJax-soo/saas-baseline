package com.baseline.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baseline.system.entity.SysRoleProject;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和项目关联表 Mapper 接口
 *
 * @author system
 */
@Mapper
public interface SysRoleProjectMapper extends BaseMapper<SysRoleProject> {

}
