package com.baseline.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.system.dto.SysUserThirdPartyBindFilterDTO;
import com.baseline.system.dto.SysUserThirdPartyUnbindDTO;
import com.baseline.system.entity.SysUserThirdPartyBind;
import com.baseline.system.entity.SysUser;
import com.baseline.system.mapper.SysUserThirdPartyBindMapper;
import com.baseline.system.mapper.SysUserMapper;
import com.baseline.system.service.ISysUserThirdPartyBindService;
import com.baseline.system.vo.SysUserThirdPartyBindVO;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户第三方绑定信息表 服务实现类
 * 
 * @author system
 */
@Service
public class SysUserThirdPartyBindServiceImpl extends ServiceImpl<SysUserThirdPartyBindMapper, SysUserThirdPartyBind> implements ISysUserThirdPartyBindService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public IPage<SysUserThirdPartyBindVO> pageBindList(SysUserThirdPartyBindFilterDTO filterDTO) {
        LambdaQueryWrapper<SysUserThirdPartyBind> queryWrapper = new LambdaQueryWrapper<>();
        
        // 用户ID过滤
        if (filterDTO.getUserId() != null) {
            queryWrapper.eq(SysUserThirdPartyBind::getUserId, filterDTO.getUserId());
        }
        
        // 第三方标识过滤
        if (StrUtil.isNotBlank(filterDTO.getProvider())) {
            queryWrapper.eq(SysUserThirdPartyBind::getProvider, filterDTO.getProvider());
        }
        
        // 按绑定时间倒序
        queryWrapper.orderByDesc(SysUserThirdPartyBind::getBindTime);
        
        IPage<SysUserThirdPartyBind> page = new Page<>(filterDTO.getPageNum(), filterDTO.getPageSize());
        IPage<SysUserThirdPartyBind> bindPage = this.page(page, queryWrapper);
        
        // 转换为VO并填充用户信息
        List<SysUserThirdPartyBindVO> voList = bindPage.getRecords().stream().map(bind -> {
            SysUserThirdPartyBindVO vo = new SysUserThirdPartyBindVO();
            BeanUtil.copyProperties(bind, vo);
            return vo;
        }).collect(Collectors.toList());
        
        // 批量查询用户信息
        if (!voList.isEmpty()) {
            List<Long> userIds = voList.stream().map(SysUserThirdPartyBindVO::getUserId).distinct().collect(Collectors.toList());
            List<SysUser> users = sysUserMapper.selectBatchIds(userIds);
            Map<Long, String> userNameMap = users.stream().collect(Collectors.toMap(SysUser::getId, SysUser::getName));
            
            // 填充用户名称
            voList.forEach(vo -> vo.setUserName(userNameMap.get(vo.getUserId())));
        }
        
        IPage<SysUserThirdPartyBindVO> result = new Page<>(bindPage.getCurrent(), bindPage.getSize(), bindPage.getTotal());
        result.setRecords(voList);
        return result;
    }

    @Override
    public boolean unbindThirdPartyUser(SysUserThirdPartyUnbindDTO unbindDTO) {
        LambdaQueryWrapper<SysUserThirdPartyBind> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserThirdPartyBind::getProvider, unbindDTO.getProvider())
                .eq(SysUserThirdPartyBind::getUserId, unbindDTO.getUserId());
        
        return this.remove(queryWrapper);
    }
}
