package com.baseline.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baseline.system.dto.SysRoleFilterDTO;
import com.baseline.system.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baseline.system.vo.SimpleRoleVO;
import com.baseline.system.vo.SysRolePageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    void updateRole(@Param("sysRole") SysRole sysRole);

    List<SimpleRoleVO> getSimpleList(Long tenantId);

    List<SimpleRoleVO> getSimpleListByUserId(Long userId);

    IPage<SysRolePageVO> page(Page<SysRolePageVO> page, @Param("dto") SysRoleFilterDTO dto);


    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectUserRoleByUserId(@Param("userId") Long userId);
}
