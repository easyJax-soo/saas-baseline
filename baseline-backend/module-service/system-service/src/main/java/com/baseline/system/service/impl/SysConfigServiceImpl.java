package com.baseline.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baseline.core.exception.BusinessException;
import com.baseline.mybatis.config.TenantProperties;
import com.baseline.system.dto.SysConfigSaveDTO;
import com.baseline.system.entity.SysConfig;
import com.baseline.system.mapper.SysConfigMapper;
import com.baseline.system.service.ISysConfigService;
import com.baseline.system.service.ISysConfigGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baseline.system.vo.SysConfigVO;
import com.baseline.system.dto.SysConfigFilterDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 系统配置 服务实现类
 *
 * @author baseline
 * @since 2023-12-06
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Autowired
    private ISysConfigGroupService sysConfigGroupService;

    @Autowired
    private TenantProperties tenantProperties;


    @Override
    public List<SysConfigVO> list(SysConfigFilterDTO dto) {
        LambdaQueryWrapper<SysConfig> query = new LambdaQueryWrapper<>();
        
        if (dto.getConfigKey() != null && !dto.getConfigKey().trim().isEmpty()) {
            query.like(SysConfig::getConfigKey, dto.getConfigKey());
        }
        if (dto.getGroupCode() != null && !dto.getGroupCode().trim().isEmpty()) {
            query.eq(SysConfig::getGroupCode, dto.getGroupCode());
        }

        List<SysConfig> list = baseMapper.selectList(query);
        return BeanUtil.copyToList(list, SysConfigVO.class);
    }

    @Override
    public boolean isConfigKeyUnique(String configKey, Long id) {
        LambdaQueryWrapper<SysConfig> query = new LambdaQueryWrapper<>();
        query.eq(SysConfig::getConfigKey, configKey);
        
        // 如果是更新操作，排除当前记录
        if (id != null) {
            query.ne(SysConfig::getId, id);
        }
        
        return baseMapper.selectCount(query) == 0;
    }

    @Override
    public Map<String, String> getConfigAll() {
        LambdaQueryWrapper<SysConfig> query = new LambdaQueryWrapper<>();
        List<SysConfig> list = baseMapper.selectList(query);

        // 将数据库配置转换为Map
        Map<String, String> configMap = list.stream()
                .collect(Collectors.toMap(SysConfig::getConfigKey, SysConfig::getConfigValue));

        configMap.put("tenantEnable", String.valueOf(tenantProperties.getEnable()));
        return configMap;
    }

    @Override
    public boolean saveOrUpdate(SysConfigSaveDTO dto) {
        // 检查配置键是否唯一
        if (!isConfigKeyUnique(dto.getConfigKey(), dto.getId())) {
            throw new BusinessException("配置键【" + dto.getConfigKey() + "】已存在，请使用其他键名");
        }
        
        SysConfig entity = BeanUtil.copyProperties(dto, SysConfig.class);
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean removeBatchByIdsWithGroupCheck(List<Long> ids) {
        // 检查每个配置项对应的分组是否为系统默认分组
        for (Long id : ids) {
            SysConfig config = baseMapper.selectById(id);
            if (config != null && config.getGroupCode() != null) {
                if (sysConfigGroupService.isSystemDefaultGroup(config.getGroupCode())) {
                    throw new RuntimeException("配置项【" + config.getName() + "】属于系统默认分组，不允许删除");
                }
            }
        }
        
        // 如果所有配置项都可以删除，则执行删除操作
        return super.removeBatchByIds(ids);
    }
}
