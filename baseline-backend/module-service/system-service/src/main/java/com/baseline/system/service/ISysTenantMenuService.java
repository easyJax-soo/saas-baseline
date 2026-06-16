package com.baseline.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.dto.*;
import com.baseline.system.entity.SysTenantMenu;
import com.baseline.system.vo.SysTenantMenuNodeVO;

import java.util.List;

/**
 * <p>
 * 租户菜单表 服务类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface ISysTenantMenuService extends IService<SysTenantMenu> {

    List<SysTenantMenuNodeVO> getTenantAdminNodeList(SysTenantMenuFilterDTO dto);

    boolean saveOrUpdate(SysTenantMenuSaveDTO dto);

    List<Long> getDetailByTenantId(Long tenantId);

    boolean remove(SysTenantMenuRemoveDTO dto);

}
