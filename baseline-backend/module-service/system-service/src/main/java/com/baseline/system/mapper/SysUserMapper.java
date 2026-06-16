package com.baseline.system.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baseline.mybatis.annotation.DataColumn;
import com.baseline.mybatis.annotation.DataPermission;
import com.baseline.system.dto.SysUserFilterDTO;
import com.baseline.system.entity.SysUser;
import com.baseline.system.vo.PageSysUserVO;
import com.baseline.system.vo.SysUserVO;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @DataPermission({
            @DataColumn(key = "deptName", value = "u.dept_id"),
            @DataColumn(key = "userName", value = "u.id"),
    })
    IPage<PageSysUserVO> pageUser(Page<PageSysUserVO> page, @Param("dto") SysUserFilterDTO dto);

    /**
     * 分页查询租户用户（依赖租户拦截器自动过滤）
     *
     * @param page 分页参数
     * @param dto 查询条件
     * @return 分页结果
     */
    @DataPermission({
            @DataColumn(key = "deptName", value = "u.dept_id"),
            @DataColumn(key = "userName", value = "u.id"),
    })
    IPage<PageSysUserVO> pageTenantUser(Page<PageSysUserVO> page, @Param("dto") SysUserFilterDTO dto);

    List<SysUserVO> getSimpleList(SysUserFilterDTO dto);

    /**
     * 根据用户ID查询租户用户（依赖租户拦截器自动过滤）
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUser selectTenantUserById(@Param("userId") Long userId);

    /**
     * 登录时根据用户名查询用户（忽略租户拦截器）
     *
     * @param username 用户名
     * @return 用户对象信息
     */
    @InterceptorIgnore(tenantLine = "true")
    SysUser selectUserForLogin(String username);
}
