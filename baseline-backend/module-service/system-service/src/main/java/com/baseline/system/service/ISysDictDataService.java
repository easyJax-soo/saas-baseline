package com.baseline.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.common.dto.DictBizDTO;
import com.baseline.common.vo.DictBizVO;
import com.baseline.system.dto.SysDictDataDTO;
import com.baseline.system.dto.SysDictDataFilterDTO;
import com.baseline.system.entity.SysDictData;
import com.baseline.system.vo.SysDictVO;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface ISysDictDataService extends IService<SysDictData> {

    /**
     * 根据字典编码获取字典数据（带缓存）
     */
    List<DictBizVO> getDictDataByDictCode(DictBizDTO dto);

    /**
     * 带缓存清除的保存或更新
     */
    boolean saveOrUpdateWithCache(SysDictDataDTO dto);

    /**
     * 带缓存清除的批量删除
     */
    boolean removeBatchByIdsWithCache(List<Long> ids);

    IPage<SysDictVO> dataPage(SysDictDataFilterDTO dto);
    
}
