package com.baseline.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baseline.system.dto.SysTenantFilterDTO;
import com.baseline.system.entity.SysTenant;
import com.baseline.system.vo.SysTenantVO;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface SysTenantMapper extends BaseMapper<SysTenant> {

    IPage<SysTenantVO> pageTenant(Page<SysTenantVO> page, @Param("dto") SysTenantFilterDTO dto);

}
