package com.baseline.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baseline.mybatis.annotation.DataColumn;
import com.baseline.mybatis.annotation.DataPermission;
import com.baseline.system.dto.SysMemberFilterDTO;
import com.baseline.system.entity.SysMember;
import com.baseline.system.vo.PageSysMemberVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员信息表 Mapper 接口
 * </p>
 *
 * @author system
 * @since 2024-10-04
 */
public interface SysMemberMapper extends BaseMapper<SysMember> {

    @DataPermission({
            @DataColumn(key = "memberName", value = "m.id"),
    })
    IPage<PageSysMemberVO> pageMember(Page<PageSysMemberVO> page, @Param("dto") SysMemberFilterDTO dto);

    /**
     * 根据账号查询会员（用于登录）
     * @param account 账号
     * @return 会员信息
     */
    SysMember selectMemberForLogin(@Param("account") String account);
}
