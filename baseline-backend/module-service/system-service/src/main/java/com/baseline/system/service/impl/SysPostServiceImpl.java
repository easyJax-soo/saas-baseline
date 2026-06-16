package com.baseline.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.system.dto.SysPostDetailDTO;
import com.baseline.system.dto.SysPostFilterDTO;
import com.baseline.system.dto.SysPostSaveDTO;
import com.baseline.system.entity.SysPost;
import com.baseline.system.mapper.SysPostMapper;
import com.baseline.system.service.ISysPostService;
import com.baseline.system.vo.SysPostOptionVO;
import com.baseline.system.vo.SysPostVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 岗位信息表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

    @Override
    public List<SysPostOptionVO> getSimpleList() {
        return baseMapper.getSimpleList();
    }

    @Override
    public boolean saveOrUpdate(SysPostSaveDTO dto) {
        SysPost entity = BeanUtil.copyProperties(dto, SysPost.class);

        int res;
        if (dto.getId() != null){
            res = baseMapper.updateById(entity);
        }else{
            res =baseMapper.insert(entity);
        }
        return res > 0;
    }

    @Override
    public SysPostVO detail(SysPostDetailDTO dto) {
        SysPost entity = baseMapper.selectById(dto.getId());
        return BeanUtil.copyProperties(entity, SysPostVO.class);
    }

    @Override
    public boolean remove(List<Long> ids) {
        int res = baseMapper.deleteBatchIds(ids);
        return res > 0;
    }

    @Override
    public IPage<SysPostVO> pagePost(SysPostFilterDTO dto) {
        Page<SysPostVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return baseMapper.page(page, dto);
    }
}
