package com.baseline.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baseline.common.constant.CommonConstants;
import com.baseline.core.exception.BusinessException;
import com.baseline.system.entity.SysConfigGroup;
import com.baseline.system.mapper.SysConfigGroupMapper;
import com.baseline.system.mapper.SysConfigMapper;
import com.baseline.system.service.ISysConfigGroupService;
import com.baseline.system.dto.SysConfigGroupSaveDTO;
import com.baseline.system.dto.SysConfigGroupFilterDTO;
import com.baseline.system.vo.SysConfigGroupVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统配置分组 服务实现类
 *
 * @author baseline
 * @since 2023-12-06
 */
@Service
public class SysConfigGroupServiceImpl extends ServiceImpl<SysConfigGroupMapper, SysConfigGroup> implements ISysConfigGroupService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public boolean isSystemDefaultGroup(String groupCode) {
        LambdaQueryWrapper<SysConfigGroup> query = new LambdaQueryWrapper<>();
        query.eq(SysConfigGroup::getGroupCode, groupCode)
             .eq(SysConfigGroup::getSysDefault, CommonConstants.SYS_DEFAULT);
        
        return baseMapper.selectCount(query) > 0;
    }

    @Override
    public boolean hasConfigItems(String groupCode) {
        // 直接通过sysConfigMapper检查该分组下是否有配置项
        LambdaQueryWrapper<com.baseline.system.entity.SysConfig> query = new LambdaQueryWrapper<>();
        query.eq(com.baseline.system.entity.SysConfig::getGroupCode, groupCode);
        return sysConfigMapper.selectCount(query) > 0;
    }

    @Override
    public List<SysConfigGroupVO> list(SysConfigGroupFilterDTO dto) {
        LambdaQueryWrapper<SysConfigGroup> query = new LambdaQueryWrapper<>();
        
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            query.like(SysConfigGroup::getName, dto.getName());
        }
        if (dto.getGroupCode() != null && !dto.getGroupCode().trim().isEmpty()) {
            query.like(SysConfigGroup::getGroupCode, dto.getGroupCode());
        }

        List<SysConfigGroup> list = baseMapper.selectList(query);
        return BeanUtil.copyToList(list, SysConfigGroupVO.class);
    }

    @Override
    public boolean saveOrUpdate(SysConfigGroupSaveDTO dto) {
        // 如果是更新操作，检查是否修改了分组编码
        if (dto.getId() != null) {
            SysConfigGroup existingGroup = baseMapper.selectById(dto.getId());
            if (existingGroup != null && !existingGroup.getGroupCode().equals(dto.getGroupCode())) {
                throw new BusinessException("分组编码不允许修改");
            }
        } else {
            // 新增时检查分组编码是否唯一
            if (!isGroupCodeUnique(dto.getGroupCode(), dto.getId())) {
                throw new BusinessException("分组编码【" + dto.getGroupCode() + "】已存在，请使用其他编码");
            }
        }
        
        SysConfigGroup entity = BeanUtil.copyProperties(dto, SysConfigGroup.class);
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean removeBatchByIdsWithCheck(List<Long> ids) {
        // 检查是否为系统默认分组和是否有配置项
        for (Long id : ids) {
            SysConfigGroup group = baseMapper.selectById(id);
            if (group != null) {
                // 检查是否为系统默认分组
                if (CommonConstants.SYS_DEFAULT.equals(group.getSysDefault())) {
                    throw new BusinessException("分组【" + group.getName() + "】是系统默认分组，不允许删除");
                }
                
                // 检查分组下是否有配置项
                if (hasConfigItems(group.getGroupCode())) {
                    throw new BusinessException("分组【" + group.getName() + "】下存在配置项，不允许删除");
                }
            }
        }
        return super.removeBatchByIds(ids);
    }

    /**
     * 检查分组编码是否唯一
     * @param groupCode 分组编码
     * @param id 当前记录ID（更新时使用，新增时传null）
     * @return 是否唯一
     */
    public boolean isGroupCodeUnique(String groupCode, Long id) {
        LambdaQueryWrapper<SysConfigGroup> query = new LambdaQueryWrapper<>();
        query.eq(SysConfigGroup::getGroupCode, groupCode);

        // 如果是更新操作，排除当前记录
        if (id != null) {
            query.ne(SysConfigGroup::getId, id);
        }

        return baseMapper.selectCount(query) == 0;
    }
}
