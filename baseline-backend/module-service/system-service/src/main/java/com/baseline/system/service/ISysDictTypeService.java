package com.baseline.system.service;

import com.baseline.system.entity.SysDictType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.vo.SysDictGroupVO;
import com.baseline.system.dto.SysDictGroupDTO;
import com.baseline.system.dto.SysDictTypeSaveDTO;

import java.util.List;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface ISysDictTypeService extends IService<SysDictType> {

    /**
     * 带缓存清除的保存或更新
     */
    boolean saveOrUpdateWithCache(SysDictTypeSaveDTO dto);

    /**
     * 带缓存清除的批量删除
     */
    boolean removeBatchByIdsWithCache(List<Long> ids);

    /**
     * 获取字典分组及其对应的字典数据项
     * @param dto 请求参数，code为空则获取所有字典分组
     * @return 字典分组列表
     */
    List<SysDictGroupVO> getDictGroups(SysDictGroupDTO dto);

}
