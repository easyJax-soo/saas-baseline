package com.baseline.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.system.entity.SysRolePermission;
import com.baseline.system.mapper.SysRolePermissionMapper;
import com.baseline.system.service.ISysRolePermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和权限关联表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

}
