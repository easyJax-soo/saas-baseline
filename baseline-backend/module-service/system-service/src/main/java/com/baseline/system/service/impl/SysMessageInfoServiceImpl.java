package com.baseline.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.common.constant.MessageInfoConstants;
import com.baseline.system.dto.SysMessageInfoFilterDTO;
import com.baseline.system.dto.SysMessageInfoImportDTO;
import com.baseline.system.dto.SysMessageInfoSaveTypeDTO;
import com.baseline.system.entity.SysTenantUser;
import com.baseline.system.service.ISysTenantUserService;
import com.baseline.system.vo.SysMessageTypeNumVO;
import com.baseline.system.entity.SysMessageInfo;
import com.baseline.system.entity.SysUserRole;
import com.baseline.system.mapper.SysMessageInfoMapper;
import com.baseline.system.service.ISysMessageInfoService;
import com.baseline.system.service.ISysUserRoleService;
import com.baseline.system.vo.SysMessageInfoExportVO;
import com.baseline.system.vo.SysMessageInfoVO;
import com.baseline.utils.security.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息中心 服务实现类
 *
 * @author bryant
 * @since 2025-11-25
 */
@Service
public class SysMessageInfoServiceImpl extends ServiceImpl<SysMessageInfoMapper, SysMessageInfo> implements ISysMessageInfoService {

    @Resource
    private ISysUserRoleService userRoleService;

    @Resource
    private ISysTenantUserService sysTenantUserService;

    @Override
    public IPage<SysMessageInfoVO> paging(Page<SysMessageInfoVO> objectPage, SysMessageInfoFilterDTO dto) {
        return baseMapper.paging(objectPage,dto);
    }

    @Override
    public List<SysMessageTypeNumVO> numOverview() {
        List<SysMessageTypeNumVO> list = baseMapper.numOverview(SecurityUtils.getUserId());
        long sum = list.stream().mapToLong(SysMessageTypeNumVO::getCount).sum();
        SysMessageTypeNumVO vo = new SysMessageTypeNumVO();
        vo.setOneCategory(0);
        vo.setCount(sum);
        list.add(vo);
        return list;
    }

    @Override
    public boolean allRead() {
        return this.lambdaUpdate()
                .eq(SysMessageInfo::getUserId, SecurityUtils.getUserId())
                .eq(SysMessageInfo::getReadStatus, MessageInfoConstants.ReadStatus.UNREAD)
                .eq(SysMessageInfo::getDeleted, 0)
                .set(SysMessageInfo::getReadStatus, MessageInfoConstants.ReadStatus.READ)
                .update();
    }

    @Override
    public boolean haveRead(Long id) {
        return this.lambdaUpdate()
                .eq(SysMessageInfo::getId, id)
                .set(SysMessageInfo::getReadStatus, MessageInfoConstants.ReadStatus.READ)
                .update();
    }

    @Override
    public Long notReadNum() {
        return this.lambdaQuery()
                .eq(SysMessageInfo::getUserId, SecurityUtils.getUserId())
                .eq(SysMessageInfo::getReadStatus, MessageInfoConstants.ReadStatus.UNREAD)
                .count();
    }


    @Override
    public SysMessageInfoVO getDetail(Long id) {
        SysMessageInfo entity = getById(id);
        if (entity == null) {
            return null;
        }
        SysMessageInfoVO vo = new SysMessageInfoVO();
        BeanUtils.copyProperties(entity, vo);
            return vo;
    }

    @Override
    public List<SysMessageInfoExportVO> exportData(SysMessageInfoFilterDTO dto){
        return baseMapper.exportData(dto);
    }

    @Override
    public Boolean importData(List<SysMessageInfoImportDTO> dataList){
        if(!CollectionUtils.isEmpty(dataList)){
        List<SysMessageInfo> list = new ArrayList<>();
            dataList.forEach(v->{
                SysMessageInfo entity = new SysMessageInfo();
                BeanUtils.copyProperties(v,entity);
                list.add(entity);
            });
            this.saveBatch(list);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean saveByType(SysMessageInfoSaveTypeDTO dto) {
        if (MessageInfoConstants.SaveType.USER.equals(dto.getSaveType())) {
            dto.getUserIds().forEach(userId -> {
                SysMessageInfo entity = new SysMessageInfo();
                BeanUtils.copyProperties(dto, entity);
                entity.setUserId(userId);
                this.save(entity);
            });
        } else if (MessageInfoConstants.SaveType.ROLE.equals(dto.getSaveType())){
            //查询角色下所有用户
            List<SysUserRole> list = userRoleService.lambdaQuery()
                    .in(SysUserRole::getRoleId, dto.getRoleIds())
                    .list();
            List<Long> userIds = list.stream()
                    .map(SysUserRole::getUserId)
                    .collect(Collectors.toList());
            sendMessageUser(dto, userIds);
        } else if (MessageInfoConstants.SaveType.ALL.equals(dto.getSaveType())) {
            //查询所有用户
            List<SysTenantUser> list = sysTenantUserService.list();
            List<Long> userIds = list.stream()
                    .map(SysTenantUser::getUserId)
                    .collect(Collectors.toList());
            sendMessageUser(dto, userIds);
        }
        return true;
    }

    @Override
    public SysMessageInfo getNewSysNotice() {
        return this.lambdaQuery()
                .eq(SysMessageInfo::getUserId, SecurityUtils.getUserId())
                .orderByDesc(SysMessageInfo::getCreateTime)
                .last("limit 1")
                .one();
    }

    @Override
    public List<SysMessageInfo> listNotice() {
        return this.lambdaQuery()
                .eq(SysMessageInfo::getUserId, SecurityUtils.getUserId())
                .orderByDesc(SysMessageInfo::getCreateTime)
                .list();
    }

    private void sendMessageUser(SysMessageInfoSaveTypeDTO dto, List<Long> userIds) {
        dto.setSaveType(MessageInfoConstants.SaveType.USER);
        dto.setUserIds(userIds);
        this.saveByType(dto);
    }
}
