package com.baseline.system.mapper;

import com.baseline.system.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu>  getMyNodeList(@Param("userId") Long userId, @Param("type") String type);

    /**
     * 根据用户ID和项目编码获取菜单列表
     *
     * @param userId 用户ID
     * @param projectCode 项目编码
     * @param type 菜单类型
     * @return 菜单列表
     */
    List<SysMenu> getMyNodeListByProject(@Param("userId") Long userId, @Param("projectCode") String projectCode, @Param("type") String type);

    /**
     * 根据角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByRoleId(Long roleId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 获取租户授权的菜单列表
     * 注意：多租户拦截器会自动拼接tenant_id条件，无需手动传递tenantId参数
     *
     * @return 菜单列表
     */
    List<SysMenu> getTenantMenuList();

    /**
     * 从sys_role_menu表获取租户用户的角色权限菜单
     *
     * @param userId 用户ID
     * @param tenantId 租户ID
     * @param type 菜单类型
     * @return 菜单列表
     */
    List<SysMenu> getMyTenantRoleNodeList(@Param("userId") Long userId, @Param("tenantId") Long tenantId, @Param("type") String type);

    /**
     * 从sys_role_menu表获取租户用户的角色权限菜单（按项目过滤）
     *
     * @param userId 用户ID
     * @param tenantId 租户ID
     * @param projectCode 项目编码
     * @param type 菜单类型
     * @return 菜单列表
     */
    List<SysMenu> getMyTenantRoleNodeListByProject(@Param("userId") Long userId, @Param("tenantId") Long tenantId, @Param("projectCode") String projectCode, @Param("type") String type);
}
