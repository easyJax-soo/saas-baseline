package com.baseline.system.service;

import com.baseline.system.dto.SysMessageInfoSaveTypeDTO;
import com.baseline.system.vo.SysMessageTypeNumVO;
import com.baseline.system.entity.SysMessageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.vo.SysMessageInfoVO;
import com.baseline.system.dto.SysMessageInfoFilterDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import  com.baseline.system.vo.SysMessageInfoExportVO;
import  com.baseline.system.dto.SysMessageInfoImportDTO;


/**
 * <p>
 * 消息中心 服务类
 * </p>
 *
 * @author bryant
 * @since 2025-11-25
 */
public interface ISysMessageInfoService extends IService<SysMessageInfo> {

    IPage<SysMessageInfoVO> paging(Page<SysMessageInfoVO> objectPage, SysMessageInfoFilterDTO dto);

    List<SysMessageTypeNumVO> numOverview();

    boolean allRead();

    boolean haveRead(Long id);

    Long notReadNum();

    SysMessageInfoVO getDetail(Long id);

    List<SysMessageInfoExportVO> exportData(SysMessageInfoFilterDTO dto);

    Boolean importData(List<SysMessageInfoImportDTO> dataList);

    boolean saveByType(SysMessageInfoSaveTypeDTO dto);

    SysMessageInfo getNewSysNotice();

    List<SysMessageInfo> listNotice();
}
