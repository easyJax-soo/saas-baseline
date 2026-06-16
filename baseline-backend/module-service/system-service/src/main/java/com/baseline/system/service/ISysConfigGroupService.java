package com.baseline.system.service;

import com.baseline.system.entity.SysConfigGroup;
import com.baseline.system.dto.SysConfigGroupSaveDTO;
import com.baseline.system.dto.SysConfigGroupFilterDTO;
import com.baseline.system.vo.SysConfigGroupVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统配置分组 服务类
 * </p>
 *
 * @author baseline
 * @since 2023-12-06
 */
public interface ISysConfigGroupService extends IService<SysConfigGroup> {

    /**
     * 根据分组编码检查是否为系统默认分组
     * @param groupCode 分组编码
     * @return 是否为系统默认分组
     */
    boolean isSystemDefaultGroup(String groupCode);

    /**
     * 检查分组下是否有配置项
     * @param groupCode 分组编码
     * @return 是否有配置项
     */
    boolean hasConfigItems(String groupCode);

    /**
     * 获取配置分组列表
     * @param dto 查询条件
     * @return 配置分组列表
     */
    List<SysConfigGroupVO> list(SysConfigGroupFilterDTO dto);

    /**
     * 保存或更新配置分组
     * @param dto 保存数据
     * @return 操作结果
     */
    boolean saveOrUpdate(SysConfigGroupSaveDTO dto);

    /**
     * 批量删除配置分组（带系统默认检查）
     * @param ids ID列表
     * @return 操作结果
     */
    boolean removeBatchByIdsWithCheck(List<Long> ids);
}
