package com.baseline.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.system.dto.SysMemberFilterDTO;
import com.baseline.system.dto.SysMemberInfoUpdateDTO;
import com.baseline.system.dto.SysMemberSaveDTO;
import com.baseline.system.entity.SysMember;
import com.baseline.system.vo.PageSysMemberVO;
import com.baseline.system.vo.SysMemberDetailVO;
import com.baseline.system.vo.SysMemberInfoVO;

import java.util.List;

/**
 * <p>
 * 会员信息表 服务类
 * </p>
 *
 * @author system
 * @since 2024-10-04
 */
public interface ISysMemberService extends IService<SysMember> {

    /**
     * 保存或更新会员
     *
     * @param dto 会员保存DTO
     * @return 是否成功
     */
    boolean saveOrUpdateMember(SysMemberSaveDTO dto);

    /**
     * 分页查询会员
     *
     * @param page 分页对象
     * @param dto 查询条件
     * @return 分页结果
     */
    IPage<PageSysMemberVO> pageMember(Page<PageSysMemberVO> page, SysMemberFilterDTO dto);

    /**
     * 删除会员
     *
     * @param ids 会员ID列表
     * @return 是否成功
     */
    boolean removeMember(List<Long> ids);

    /**
     * 获取会员详情
     *
     * @param id 会员ID
     * @return 会员详情
     */
    SysMemberDetailVO getMemberDetail(Long id);

    /**
     * 重置会员密码
     *
     * @param memberId 会员ID
     * @param newPassword 新密码
     * @return 是否成功
     */
    boolean resetPassword(Long memberId, String newPassword);

    /**
     * 根据用户名获取登录会员信息
     *
     * @param dto 登录用户DTO
     * @return 登录用户信息
     */
    LoginUserBizVO getLoginUserByUsername(LoginUserBizDTO dto);

    /**
     * 获取会员简单列表
     *
     * @param dto 查询条件
     * @return 会员列表
     */
    List<SysMemberDetailVO> getSimpleList(SysMemberFilterDTO dto);

    /**
     * 获取会员完整信息（包含实名认证信息）
     *
     * @param memberId 会员ID
     * @return 会员完整信息
     */
    SysMemberInfoVO getMemberInfo(Long memberId);

    /**
     * 更新会员信息
     *
     * @param dto 更新信息DTO
     * @return 是否成功
     */
    boolean updateMemberInfo(SysMemberInfoUpdateDTO dto);
}
