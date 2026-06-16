package com.baseline.system.mapper;

import com.baseline.system.vo.SysMessageTypeNumVO;
import com.baseline.system.entity.SysMessageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baseline.system.vo.SysMessageInfoExportVO;
import com.baseline.system.vo.SysMessageInfoVO;
import com.baseline.system.dto.SysMessageInfoFilterDTO;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 消息中心 Mapper 接口
 *
 * @author bryant
 * @since 2025-11-25
 */
public interface SysMessageInfoMapper extends BaseMapper<SysMessageInfo> {
    IPage<SysMessageInfoVO> paging(Page<SysMessageInfoVO> objectPage, @Param("dto") SysMessageInfoFilterDTO dto);
    List<SysMessageTypeNumVO> numOverview(@Param("userId") Long userId);

    List<SysMessageInfoVO> getList(@Param("dto") SysMessageInfoFilterDTO dto);

    List<SysMessageInfoExportVO> exportData(@Param("dto") SysMessageInfoFilterDTO dto);

}