package com.baseline.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.system.dto.SysMemberThirdPartyBindFilterDTO;
import com.baseline.system.dto.SysMemberThirdPartyUnbindDTO;
import com.baseline.system.entity.SysMemberThirdPartyBind;
import com.baseline.system.entity.SysMember;
import com.baseline.system.mapper.SysMemberThirdPartyBindMapper;
import com.baseline.system.mapper.SysMemberMapper;
import com.baseline.system.service.ISysMemberThirdPartyBindService;
import com.baseline.system.vo.SysMemberThirdPartyBindVO;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 会员第三方绑定信息表 服务实现类
 * 
 * @author system
 */
@Service
public class SysMemberThirdPartyBindServiceImpl extends ServiceImpl<SysMemberThirdPartyBindMapper, SysMemberThirdPartyBind> implements ISysMemberThirdPartyBindService {

    @Autowired
    private SysMemberMapper sysMemberMapper;

    @Override
    public IPage<SysMemberThirdPartyBindVO> pageBindList(SysMemberThirdPartyBindFilterDTO filterDTO) {
        LambdaQueryWrapper<SysMemberThirdPartyBind> queryWrapper = new LambdaQueryWrapper<>();
        
        // 会员ID过滤
        if (filterDTO.getMemberId() != null) {
            queryWrapper.eq(SysMemberThirdPartyBind::getMemberId, filterDTO.getMemberId());
        }
        
        // 第三方标识过滤
        if (StrUtil.isNotBlank(filterDTO.getProvider())) {
            queryWrapper.eq(SysMemberThirdPartyBind::getProvider, filterDTO.getProvider());
        }
        
        // 按绑定时间倒序
        queryWrapper.orderByDesc(SysMemberThirdPartyBind::getBindTime);
        
        IPage<SysMemberThirdPartyBind> page = new Page<>(filterDTO.getPageNum(), filterDTO.getPageSize());
        IPage<SysMemberThirdPartyBind> bindPage = this.page(page, queryWrapper);
        
        // 转换为VO并填充会员信息
        List<SysMemberThirdPartyBindVO> voList = bindPage.getRecords().stream().map(bind -> {
            SysMemberThirdPartyBindVO vo = new SysMemberThirdPartyBindVO();
            BeanUtil.copyProperties(bind, vo);
            return vo;
        }).collect(Collectors.toList());
        
        // 批量查询会员信息
        if (!voList.isEmpty()) {
            List<Long> memberIds = voList.stream().map(SysMemberThirdPartyBindVO::getMemberId).distinct().collect(Collectors.toList());
            List<SysMember> members = sysMemberMapper.selectBatchIds(memberIds);
            Map<Long, String> memberNameMap = members.stream().collect(Collectors.toMap(SysMember::getId, SysMember::getName));
            
            // 填充会员名称
            voList.forEach(vo -> vo.setMemberName(memberNameMap.get(vo.getMemberId())));
        }
        
        IPage<SysMemberThirdPartyBindVO> result = new Page<>(bindPage.getCurrent(), bindPage.getSize(), bindPage.getTotal());
        result.setRecords(voList);
        return result;
    }

    @Override
    public boolean unbindThirdPartyMember(SysMemberThirdPartyUnbindDTO unbindDTO) {
        LambdaQueryWrapper<SysMemberThirdPartyBind> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMemberThirdPartyBind::getProvider, unbindDTO.getProvider())
                .eq(SysMemberThirdPartyBind::getMemberId, unbindDTO.getMemberId());
        
        return this.remove(queryWrapper);
    }
}