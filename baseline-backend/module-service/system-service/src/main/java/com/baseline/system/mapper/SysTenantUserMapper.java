package com.baseline.system.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baseline.system.dto.SysTenantUserFilterDTO;
import com.baseline.system.dto.SysTenantUnboundUserFilterDTO;
import com.baseline.system.entity.SysTenantUser;
import com.baseline.system.entity.SysUser;
import com.baseline.system.vo.SysTenantUserDetailVO;
import com.baseline.system.vo.SysTenantUnboundUserVO;
import com.baseline.system.vo.UserTenantVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 租户用户信息表 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface SysTenantUserMapper extends BaseMapper<SysTenantUser> {

    IPage<SysTenantUserDetailVO> pageTenantUser(Page<SysTenantUserDetailVO> page, @Param("dto") SysTenantUserFilterDTO dto);

    SysTenantUserDetailVO getDetailById(@Param("id") Long id);

    /**
     * 获取用户可访问的租户列表
     * 注意：此方法需要忽略租户拦截器，因为需要查询用户的所有租户，而不是当前租户
     *
     * @param userId 用户ID
     * @return 用户可访问的租户列表
     */
    @InterceptorIgnore(tenantLine = "true")
    List<UserTenantVO> getUserTenantList(@Param("userId") Long userId);

    /**
     * 获取租户未绑定的用户列表
     *
     * @param page 分页参数
     * @param dto 查询参数
     * @return 未绑定用户分页列表
     */
    IPage<SysTenantUnboundUserVO> getUnboundUsers(Page<SysTenantUnboundUserVO> page, @Param("dto") SysTenantUnboundUserFilterDTO dto);

    /**
     * 获取租户关联的用户列表（用于下拉选项）
     * 注意：多租户拦截器会自动添加tenant_id条件
     *
     * @return 用户列表
     */
    List<SysUser> selectTenantUsers();
}
