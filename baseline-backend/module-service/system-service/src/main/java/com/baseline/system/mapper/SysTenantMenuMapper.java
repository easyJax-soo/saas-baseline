package com.baseline.system.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baseline.system.entity.SysTenantMenu;
import com.baseline.system.vo.SysTenantMenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 租户菜单信息表 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface SysTenantMenuMapper extends BaseMapper<SysTenantMenu> {

    List<SysTenantMenuVO> selectTenantMenu();

    /**
     * 查询指定租户的菜单（忽略租户拦截器，用于系统管理员查询任意租户资源）
     */
    @InterceptorIgnore(tenantLine = "true")
    List<SysTenantMenuVO> selectTenantMenuByTenantId(@Param("tenantId")Long tenantId);

    List<SysTenantMenuVO> selectTenantMemberNodeList(@Param("tenantId")Long tenantId, @Param("tenantMemberId")Long tenantMemberId);

}
