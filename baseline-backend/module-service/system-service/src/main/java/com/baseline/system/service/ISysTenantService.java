package com.baseline.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.dto.SysTenantDetailDTO;
import com.baseline.system.dto.SysTenantFilterDTO;
import com.baseline.system.dto.SysTenantResourceDTO;
import com.baseline.system.dto.SysTenantResourceSaveDTO;
import com.baseline.system.dto.SysTenantSaveDTO;
import com.baseline.system.entity.SysTenant;
import com.baseline.system.vo.SysTenantDetailVO;
import com.baseline.system.vo.SysTenantResourceVO;
import com.baseline.system.vo.SysTenantTreeVO;
import com.baseline.system.vo.SysTenantVO;

import java.util.List;

/**
 * <p>
 * 租户信息表 服务类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface ISysTenantService extends IService<SysTenant> {

    boolean saveOrUpdate(SysTenantSaveDTO dto);

    SysTenantVO detail(SysTenantDetailDTO dto);

    boolean remove(List<Long> ids);

    IPage<SysTenantVO> pageTenant(SysTenantFilterDTO dto);

    /**
     * 获取租户资源详情（权限、菜单、项目）
     *
     * @param dto 查询参数
     * @return 租户资源详情
     */
    SysTenantResourceVO getTenantResource(SysTenantResourceDTO dto);

    /**
     * 保存租户资源（权限、菜单、项目）
     *
     * @param dto 保存参数
     * @return 是否成功
     */
    boolean saveTenantResource(SysTenantResourceSaveDTO dto);

    // ========== 层级相关方法 ==========

    /**
     * 获取租户树形结构
     *
     * @param parentId 父租户ID，null表示获取所有
     * @return 租户树形列表
     */
    List<SysTenantTreeVO> getTenantTree(Long parentId);

    /**
     * 获取租户详情（包含层级信息）
     *
     * @param id 租户ID
     * @return 租户详情
     */
    SysTenantDetailVO getTenantDetail(Long id);

    /**
     * 获取租户的所有子租户ID（包括自己）
     *
     * @param tenantId 租户ID
     * @return 租户ID列表
     */
    List<Long> getTenantAndChildrenIds(Long tenantId);

    /**
     * 获取租户的所有父租户ID（包括自己）
     *
     * @param tenantId 租户ID
     * @return 租户ID列表
     */
    List<Long> getTenantAndParentIds(Long tenantId);

    /**
     * 检查是否可以设置为父租户（避免循环引用）
     *
     * @param tenantId 当前租户ID
     * @param parentId 要设置的父租户ID
     * @return 是否可以设置
     */
    boolean canSetParent(Long tenantId, Long parentId);

    /**
     * 获取租户列表
     *
     * @param dto 查询条件
     * @return 租户列表
     */
    List<SysTenantVO> getTenantList(SysTenantFilterDTO dto);

    /**
     * 获取租户树形结构（带过滤条件）
     *
     * @param dto 查询条件
     * @return 租户树形结构列表
     */
    List<SysTenantTreeVO> getTenantTree(SysTenantFilterDTO dto);

    /**
     * 获取租户详情
     * 支持通过ID或编码查询
     *
     * @param dto 查询条件
     * @return 租户详情
     */
    SysTenantVO getTenantDetail(SysTenantDetailDTO dto);
}
