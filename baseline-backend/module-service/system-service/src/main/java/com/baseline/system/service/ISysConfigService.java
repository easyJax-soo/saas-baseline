package com.baseline.system.service;

import com.baseline.system.dto.SysConfigSaveDTO;
import com.baseline.system.entity.SysConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.vo.SysConfigVO;
import com.baseline.system.dto.SysConfigFilterDTO;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统配置 服务类
 * </p>
 *
 * @author baseline
 * @since 2023-12-06
 */
public interface ISysConfigService extends IService<SysConfig> {

    /**
     * 获取配置项列表
     * @param dto 查询条件
     * @return 配置项列表
     */
    List<SysConfigVO> list(SysConfigFilterDTO dto);

    /**
     * 检查配置键是否唯一
     * @param configKey 配置键
     * @param id 当前记录ID（更新时使用，新增时传null）
     * @return 是否唯一
     */
    boolean isConfigKeyUnique(String configKey, Long id);

    Map<String, String> getConfigAll();

    boolean saveOrUpdate(SysConfigSaveDTO dto);

    /**
     * 批量删除配置项，如果对应的分组是系统默认的，则不允许删除
     * @param ids 配置项ID列表
     * @return 删除结果
     */
    boolean removeBatchByIdsWithGroupCheck(List<Long> ids);

}
