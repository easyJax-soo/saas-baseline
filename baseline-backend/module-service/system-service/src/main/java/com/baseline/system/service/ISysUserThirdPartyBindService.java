package com.baseline.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.dto.SysUserThirdPartyBindFilterDTO;
import com.baseline.system.dto.SysUserThirdPartyUnbindDTO;
import com.baseline.system.entity.SysUserThirdPartyBind;
import com.baseline.system.vo.SysUserThirdPartyBindVO;

/**
 * 用户第三方绑定信息表 服务类
 * 
 * @author system
 */
public interface ISysUserThirdPartyBindService extends IService<SysUserThirdPartyBind> {

    /**
     * 分页查询用户第三方绑定列表
     * @param filterDTO 查询条件
     * @return 分页结果
     */
    IPage<SysUserThirdPartyBindVO> pageBindList(SysUserThirdPartyBindFilterDTO filterDTO);

    /**
     * 解绑用户第三方账号
     * 
     * @param unbindDTO 解绑参数
     * @return 是否成功
     */
    boolean unbindThirdPartyUser(SysUserThirdPartyUnbindDTO unbindDTO);
}
