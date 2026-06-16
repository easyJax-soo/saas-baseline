package com.baseline.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.dto.SysTenantUserDetailDTO;
import com.baseline.system.dto.SysTenantUserFilterDTO;
import com.baseline.system.dto.SysTenantUserBindDTO;
import com.baseline.system.dto.SysTenantUserUnbindDTO;
import com.baseline.system.dto.SysTenantUnboundUserFilterDTO;
import com.baseline.system.entity.SysTenantUser;
import com.baseline.system.vo.SysTenantUserDetailVO;
import com.baseline.system.vo.SysTenantUnboundUserVO;

import java.util.List;

/**
 * <p>
 * 租户信息表 服务类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface ISysTenantUserService extends IService<SysTenantUser> {

    SysTenantUserDetailVO detail(SysTenantUserDetailDTO dto);

    boolean remove(List<Long> ids);

    IPage<SysTenantUserDetailVO> pageTenantUser(SysTenantUserFilterDTO dto);

    /**
     * 绑定现有用户到租户
     *
     * @param dto 绑定参数
     * @return 是否成功
     */
    boolean bindUserToTenant(SysTenantUserBindDTO dto);

    /**
     * 解绑租户用户（使用DTO）
     *
     * @param dto 解绑参数
     * @return 是否成功
     */
    boolean unbindTenantUsers(SysTenantUserUnbindDTO dto);

    /**
     * 获取租户未绑定的用户列表
     *
     * @param dto 查询参数
     * @return 未绑定用户分页列表
     */
    IPage<SysTenantUnboundUserVO> getUnboundUsers(SysTenantUnboundUserFilterDTO dto);
}
