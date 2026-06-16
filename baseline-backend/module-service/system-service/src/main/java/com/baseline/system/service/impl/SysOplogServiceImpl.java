package com.baseline.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baseline.common.dto.SysOperLogBizDTO;
import com.baseline.system.dto.SysOplogFilterDTO;
import com.baseline.system.entity.SysOplog;
import com.baseline.system.mapper.SysOplogMapper;
import com.baseline.system.service.ISysOplogService;
import com.baseline.system.vo.SysOplogDetailVO;
import com.baseline.system.vo.SysOplogPageVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Service
public class SysOplogServiceImpl extends ServiceImpl<SysOplogMapper, SysOplog> implements ISysOplogService {

    @Override
    public IPage<SysOplogPageVO> pageOplog(SysOplogFilterDTO dto) {
        Page<SysOplogPageVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return baseMapper.page(page, dto);
    }

    @Override
    public SysOplogDetailVO getOplogDetail(Long id) {
        SysOplog sysOplog = this.getById(id);
        if (sysOplog == null) {
            return null;
        }
        
        SysOplogDetailVO detailVO = new SysOplogDetailVO();
        BeanUtils.copyProperties(sysOplog, detailVO);
        return detailVO;
    }

    @Override
    public boolean saveOperLog(SysOperLogBizDTO sysOperLogBizDTO) {
        if (sysOperLogBizDTO == null) {
            return false;
        }
        
        try {
            SysOplog sysOplog = new SysOplog();
            BeanUtils.copyProperties(sysOperLogBizDTO, sysOplog);
            
            // 如果没有设置创建时间，则设置当前时间
            if (sysOplog.getCreateTime() == null) {
                sysOplog.setCreateTime(LocalDateTime.now());
            }
            
            return this.save(sysOplog);
        } catch (Exception e) {
            // 日志保存失败不应该影响业务流程，只记录错误
            e.printStackTrace();
            return false;
        }
    }

}
