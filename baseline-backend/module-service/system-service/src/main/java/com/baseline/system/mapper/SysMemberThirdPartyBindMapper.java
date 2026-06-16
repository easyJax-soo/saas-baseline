package com.baseline.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baseline.system.entity.SysMemberThirdPartyBind;
import org.apache.ibatis.annotations.Param;

/**
 * 会员第三方绑定信息表 Mapper 接口
 * 
 * @author system
 */
public interface SysMemberThirdPartyBindMapper extends BaseMapper<SysMemberThirdPartyBind> {

    /**
     * 根据会员ID查询绑定记录
     *
     * @param memberId 会员ID
     * @return 绑定记录列表
     */
    java.util.List<SysMemberThirdPartyBind> selectByMemberId(@Param("memberId") Long memberId);
}
