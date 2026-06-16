package com.baseline.system.mapper;

import com.baseline.common.dto.UserPermissionBizDTO;
import com.baseline.common.vo.UserPermissionBizVO;
import com.baseline.system.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限表 Mapper 接口
 *
 * @author baseline
 * @since 2023-12-05
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermission> selectPermsByUserId(@Param("userId")Long userId);

    List<UserPermissionBizVO> getPermissionsByUserIdAndRoleId(@Param("dto") UserPermissionBizDTO dto);

    /**
     * 通过用户 ID和多个角色ID获取权限列表
     */
    List<UserPermissionBizVO> getPermissionsByUserIdAndRoleIds(@Param("dto") UserPermissionBizDTO dto);

    /**
     * 获取租户管理员权限
     */
    List<UserPermissionBizVO> getTenantAdminPermissions(@Param("userId") Long userId);
}