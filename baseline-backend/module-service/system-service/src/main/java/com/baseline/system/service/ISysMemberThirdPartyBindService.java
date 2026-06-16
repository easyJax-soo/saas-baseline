package com.baseline.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.dto.SysMemberThirdPartyBindFilterDTO;
import com.baseline.system.dto.SysMemberThirdPartyUnbindDTO;
import com.baseline.system.entity.SysMemberThirdPartyBind;
import com.baseline.system.vo.SysMemberThirdPartyBindVO;

/**
 * 会员第三方绑定信息表 服务类
 * 
 * @author system
 */
public interface ISysMemberThirdPartyBindService extends IService<SysMemberThirdPartyBind> {

    /**
     * 分页查询会员第三方绑定列表
     * @param filterDTO 查询条件
     * @return 分页结果
     */
    IPage<SysMemberThirdPartyBindVO> pageBindList(SysMemberThirdPartyBindFilterDTO filterDTO);

    /**
     * 解绑会员第三方账号
     * 
     * @param unbindDTO 解绑参数
     * @return 是否成功
     */
    boolean unbindThirdPartyMember(SysMemberThirdPartyUnbindDTO unbindDTO);
}