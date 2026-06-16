package com.baseline.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baseline.system.dto.SysOplogFilterDTO;
import com.baseline.system.entity.SysOplog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baseline.system.vo.SysOplogPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 操作日志记录 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface SysOplogMapper extends BaseMapper<SysOplog> {

    IPage<SysOplogPageVO> page(Page<SysOplogPageVO> page, @Param("dto") SysOplogFilterDTO dto);
}
