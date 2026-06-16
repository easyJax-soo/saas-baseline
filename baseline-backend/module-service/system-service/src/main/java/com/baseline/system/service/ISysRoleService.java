package com.baseline.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.common.dto.UserRoleBizDTO;
import com.baseline.common.vo.UserRoleBizVO;
import com.baseline.system.dto.SysRoleDetailDTO;
import com.baseline.system.dto.SysRoleFilterDTO;
import com.baseline.system.dto.SysRoleSaveDTO;
import com.baseline.system.entity.SysRole;
import com.baseline.system.vo.SimpleRoleVO;
import com.baseline.system.vo.SysRolePageVO;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface ISysRoleService extends IService<SysRole> {

    boolean saveOrUpdateRole(SysRoleSaveDTO dto);

    SysRoleSaveDTO detail(SysRoleDetailDTO dto);

    boolean removeSysRole(List<Long> ids);

    List<SimpleRoleVO> getSimpleList();

    List<SimpleRoleVO> getSimpleListByUserId(Long userId);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<SysRole> selectUserRoleByUserId(Long userId);

    List<UserRoleBizVO> getRolesByUserId(UserRoleBizDTO dto);

    IPage<SysRolePageVO> pageRole(SysRoleFilterDTO dto);

}
