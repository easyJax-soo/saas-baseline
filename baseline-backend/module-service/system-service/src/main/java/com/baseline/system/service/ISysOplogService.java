package com.baseline.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baseline.common.dto.SysOperLogBizDTO;
import com.baseline.system.dto.SysOplogFilterDTO;
import com.baseline.system.entity.SysOplog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.vo.SysOplogDetailVO;
import com.baseline.system.vo.SysOplogPageVO;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface ISysOplogService extends IService<SysOplog> {

    IPage<SysOplogPageVO> pageOplog(SysOplogFilterDTO dto);

    SysOplogDetailVO getOplogDetail(Long id);

    /**
     * 保存操作日志
     * @param sysOperLogBizDTO 操作日志业务DTO
     * @return 是否成功
     */
    boolean saveOperLog(SysOperLogBizDTO sysOperLogBizDTO);

}
