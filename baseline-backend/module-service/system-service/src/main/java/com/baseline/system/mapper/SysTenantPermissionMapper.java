package com.baseline.system.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baseline.system.entity.SysTenantPermission;
import com.baseline.system.vo.SysTenantPermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 租户权限信息表 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface SysTenantPermissionMapper extends BaseMapper<SysTenantPermission> {

    List<SysTenantPermissionVO> getTenantPermission(Long tenantId);


    List<SysTenantPermissionVO> selectTenantPermission();

    /**
     * 查询指定租户的权限（忽略租户拦截器，用于系统管理员查询任意租户资源）
     */
    @InterceptorIgnore(tenantLine = "true")
    List<SysTenantPermissionVO> selectTenantPermissionByTenantId(@Param("tenantId")Long tenantId);
}
