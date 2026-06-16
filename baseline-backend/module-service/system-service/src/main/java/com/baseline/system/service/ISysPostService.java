package com.baseline.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.dto.SysPostDetailDTO;
import com.baseline.system.dto.SysPostFilterDTO;
import com.baseline.system.dto.SysPostSaveDTO;
import com.baseline.system.entity.SysPost;
import com.baseline.system.vo.SysPostOptionVO;
import com.baseline.system.vo.SysPostVO;

import java.util.List;

/**
 * <p>
 * 岗位信息表 服务类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface ISysPostService extends IService<SysPost> {
    List<SysPostOptionVO> getSimpleList();

    boolean saveOrUpdate(SysPostSaveDTO dto);

    SysPostVO detail(SysPostDetailDTO dto);

    boolean remove(List<Long> ids);

    IPage<SysPostVO> pagePost(SysPostFilterDTO dto);
}
