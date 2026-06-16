package com.baseline.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baseline.system.dto.SysPostFilterDTO;
import com.baseline.system.entity.SysPost;
import com.baseline.system.vo.SysPostOptionVO;
import com.baseline.system.vo.SysPostVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 用户岗位表 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface SysPostMapper extends BaseMapper<SysPost> {

    IPage<SysPostVO> page(Page<SysPostVO> page, @Param("dto") SysPostFilterDTO dto);

    List<SysPostOptionVO> getSimpleList();
}
