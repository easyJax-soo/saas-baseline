package com.baseline.system.service;

import com.baseline.system.dto.SysMenuFilterDTO;
import com.baseline.system.dto.SysMenuQueryDTO;
import com.baseline.system.dto.SysMenuSaveDTO;
import com.baseline.system.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.vo.SysMenuNodeVO;
import com.baseline.system.vo.SysMenuVO;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenuNodeVO> getNodeList(SysMenuFilterDTO dto);

    List<SysMenuVO> getBtnNodeList(Long id);

    /**
     * 获取当前用户的菜单列表
     *
     * @param dto 查询条件（可选，如果传入projectCode则按项目过滤）
     * @return 菜单列表
     */
    List<SysMenuNodeVO> getMyNodeList(SysMenuQueryDTO dto);

    List<String> getBtnPermission(Long userId, Boolean isAdmin);

    /**
     * 获取当前用户的按钮权限
     *
     * @param dto 查询条件（可选，如果传入projectCode则按项目过滤）
     * @return 权限列表
     */
    List<String> getMyBtnPermission(SysMenuQueryDTO dto);

    boolean saveOrUpdate(SysMenuSaveDTO dto);

    /**
     * 安全删除菜单（检查是否有子节点）
     *
     * @param ids 菜单ID列表
     * @return 删除结果
     */
    boolean safeRemoveByIds(List<Long> ids);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByUserId(Long userId);
}
