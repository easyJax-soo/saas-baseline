package com.baseline.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.PointOrganizeUserBindBIzDTO;
import com.baseline.common.service.IPointsBizService;
import com.baseline.common.vo.PointVillageOrganizationBizVO;
import com.baseline.core.exception.BusinessException;
import com.baseline.core.utils.StringUtils;
import com.baseline.system.dto.SysMemberFilterDTO;
import com.baseline.system.dto.SysMemberInfoUpdateDTO;
import com.baseline.system.dto.SysMemberSaveDTO;
import com.baseline.system.entity.SysMember;
import com.baseline.system.entity.SysMemberRealNameAuth;
import com.baseline.system.entity.SysMemberThirdPartyBind;
import com.baseline.system.entity.SysTenant;
import com.baseline.system.enums.RealNameAuthStatusEnum;
import com.baseline.system.mapper.SysMemberMapper;
import com.baseline.system.service.ISysMemberService;
import com.baseline.system.service.ISysMemberThirdPartyBindService;
import com.baseline.system.service.ISysMemberRealNameAuthService;
import com.baseline.system.service.ISysTenantService;
import com.baseline.system.vo.PageSysMemberVO;
import com.baseline.system.vo.SysMemberDetailVO;
import com.baseline.system.vo.SysMemberInfoVO;
import com.baseline.utils.security.SecurityUtils;
import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.utils.security.SaTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 会员信息表 服务实现类
 * </p>
 *
 * @author system
 * @since 2024-10-04
 */
@Service
public class SysMemberServiceImpl extends ServiceImpl<SysMemberMapper, SysMember> implements ISysMemberService {

    @Autowired
    private ISysMemberThirdPartyBindService sysMemberThirdPartyBindService;

    @Autowired
    private ISysMemberRealNameAuthService sysMemberRealNameAuthService;

    @Autowired
    private ISysTenantService sysTenantService;

    @Resource
    private IPointsBizService pointsBizService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateMember(SysMemberSaveDTO dto) {
        SysMember member = new SysMember();
        BeanUtil.copyProperties(dto, member);

        // 新增时检查账号是否存在
        if (ObjectUtil.isNull(dto.getId())) {
            // 检查账号是否存在
            LambdaQueryWrapper<SysMember> accountWrapper = new LambdaQueryWrapper<>();
            accountWrapper.eq(SysMember::getAccount, dto.getAccount());
            accountWrapper.eq(SysMember::getDeleted, false);
            if (count(accountWrapper) > 0) {
                throw new BusinessException("账号已存在");
            }

            // 设置密码并加密
            if (StringUtils.isNotEmpty(dto.getPassword())) {
                passwordValidator(dto.getPassword());
                String salt = RandomUtil.randomString(6);
                member.setSalt(salt);
                member.setPassword(SecurityUtils.encryptPassword(dto.getPassword(), salt));
            }
        } else {
            // 更新时检查账号是否存在
            LambdaQueryWrapper<SysMember> accountWrapper = new LambdaQueryWrapper<>();
            accountWrapper.eq(SysMember::getAccount, dto.getAccount());
            accountWrapper.eq(SysMember::getDeleted, false);
            accountWrapper.ne(SysMember::getId, dto.getId());
            if (count(accountWrapper) > 0) {
                throw new BusinessException("账号已存在");
            }

            // 更新时如果有新密码则加密
            if (StringUtils.isNotEmpty(dto.getPassword())) {
                passwordValidator(dto.getPassword());
                String salt = RandomUtil.randomString(6);
                member.setSalt(salt);
                member.setPassword(SecurityUtils.encryptPassword(dto.getPassword(), salt));
            }
        }

        boolean result = saveOrUpdate(member);

        return result;
    }

    @Override
    public IPage<PageSysMemberVO> pageMember(Page<PageSysMemberVO> page, SysMemberFilterDTO dto) {
        IPage<PageSysMemberVO> result = baseMapper.pageMember(page, dto);
        
        // 填充第三方绑定信息和实名认证信息
        if (CollectionUtil.isNotEmpty(result.getRecords())) {
            List<Long> memberIds = result.getRecords().stream()
                .map(PageSysMemberVO::getId)
                .collect(Collectors.toList());
            
            // 查询这些会员的第三方绑定情况
            LambdaQueryWrapper<SysMemberThirdPartyBind> bindQueryWrapper = new LambdaQueryWrapper<>();
            bindQueryWrapper.in(SysMemberThirdPartyBind::getMemberId, memberIds);
            List<SysMemberThirdPartyBind> bindList = sysMemberThirdPartyBindService.list(bindQueryWrapper);
            
            // 构建会员ID到绑定状态的映射
            Set<Long> boundMemberIds = bindList.stream()
                .map(SysMemberThirdPartyBind::getMemberId)
                .collect(Collectors.toSet());
            
            // 批量获取会员实名认证状态
            Map<Long, Integer> memberAuthStatusMap = sysMemberRealNameAuthService.batchGetMemberAuthStatus(memberIds);
            
            // 设置绑定状态和实名认证状态
            result.getRecords().forEach(member -> {
                member.setHasThirdPartyBind(boundMemberIds.contains(member.getId()));
                
                // 设置实名认证状态
                Integer authStatus = memberAuthStatusMap.get(member.getId());
                if (authStatus != null) {
                    member.setRealNameAuthStatus(authStatus);
                    // 只有认证通过时，hasRealNameAuth才为true
                    member.setHasRealNameAuth(authStatus.equals(RealNameAuthStatusEnum.APPROVED.getCode()));
                } else {
                    member.setHasRealNameAuth(false);
                    member.setRealNameAuthStatus(null);
                }
            });
        }
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeMember(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return false;
        }

        // 逻辑删除会员
        return removeByIds(ids);
    }

    @Override
    public SysMemberDetailVO getMemberDetail(Long id) {
        SysMember member = getById(id);
        if (ObjectUtil.isNull(member)) {
            throw new BusinessException("会员不存在");
        }

        SysMemberDetailVO detailVO = new SysMemberDetailVO();
        BeanUtil.copyProperties(member, detailVO);
        
        // 填充租户名称
        if (member.getTenantId() != null) {
            SysTenant tenant = sysTenantService.getById(member.getTenantId());
            if (tenant != null) {
                detailVO.setTenantName(tenant.getName());
            }
        }
        
        // 查询第三方绑定情况
        LambdaQueryWrapper<SysMemberThirdPartyBind> bindQueryWrapper = new LambdaQueryWrapper<>();
        bindQueryWrapper.eq(SysMemberThirdPartyBind::getMemberId, id);
        long bindCount = sysMemberThirdPartyBindService.count(bindQueryWrapper);
        detailVO.setHasThirdPartyBind(bindCount > 0);
        
        // 获取实名认证状态
        SysMemberRealNameAuth authInfo = sysMemberRealNameAuthService.getByMemberId(id);
        if (authInfo != null) {
            detailVO.setRealNameAuthStatus(authInfo.getAuthStatus());
            // 只有认证通过时，hasRealNameAuth才为true
            detailVO.setHasRealNameAuth(authInfo.getAuthStatus().equals(RealNameAuthStatusEnum.APPROVED.getCode()));
        } else {
            detailVO.setHasRealNameAuth(false);
            detailVO.setRealNameAuthStatus(null);
        }
        
        return detailVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(Long memberId, String newPassword) {
        passwordValidator(newPassword);
        
        SysMember member = getById(memberId);
        if (ObjectUtil.isNull(member)) {
            throw new BusinessException("会员不存在");
        }

        String salt = RandomUtil.randomString(6);
        member.setSalt(salt);
        member.setPassword(SecurityUtils.encryptPassword(newPassword, salt));
        member.setUpdateTime(LocalDateTime.now());

        return updateById(member);
    }

    /**
     * 密码强度验证
     * @param password 密码
     */
    public void passwordValidator(String password){
        SecurityUtils.passwordValidator(password);
    }

    @Override
    public LoginUserBizVO getLoginUserByUsername(LoginUserBizDTO dto) {
        // 使用专门的登录查询方法，忽略租户拦截器
        SysMember member = baseMapper.selectMemberForLogin(dto.getUsername());

        if(ObjectUtil.isNull(member)){
            throw new BusinessException("会员不存在");
        }

        LoginUserBizVO loginUser = BeanUtil.copyProperties(member, LoginUserBizVO.class);
        loginUser.setUsername(member.getAccount());
        loginUser.setPassword(member.getPassword()); // 设置密码用于认证
        loginUser.setSalt(member.getSalt()); // 设置盐值用于认证
        loginUser.setLoginType(SaTokenUtils.LOGIN_MEMBER);

        // 会员暂时没有角色权限系统，可以根据需要扩展
        // 这里可以添加会员相关的权限逻辑
        loginUser.setRoleIds(new ArrayList<>());
        loginUser.setRoles(new ArrayList<>());
        loginUser.setMenus(new ArrayList<>());
        loginUser.setPermissions(new ArrayList<>());
        loginUser.setDeptIds(new ArrayList<>());
        loginUser.setDeptCodes(new ArrayList<>());
        loginUser.setTenantId(0L);
        return loginUser;
    }

    @Override
    public List<SysMemberDetailVO> getSimpleList(SysMemberFilterDTO dto) {
        // 构建查询条件
        LambdaQueryWrapper<SysMember> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.isNotBlank(dto.getName())) {
            wrapper.like(SysMember::getName, dto.getName());
        }
        if (StringUtils.isNotBlank(dto.getPhone())) {
            wrapper.like(SysMember::getPhone, dto.getPhone());
        }
        if (dto.getStatus() != null) {
            wrapper.eq(SysMember::getStatus, dto.getStatus());
        }
        if (dto.getBeginTime() != null) {
            wrapper.ge(SysMember::getCreateTime, dto.getBeginTime());
        }
        if (dto.getEndTime() != null) {
            wrapper.le(SysMember::getCreateTime, dto.getEndTime());
        }
        
        // 查询会员列表
        List<SysMember> memberList = list(wrapper);
        
        // 转换为VO
        return BeanUtil.copyToList(memberList, SysMemberDetailVO.class);
    }

    @Override
    public SysMemberInfoVO getMemberInfo(Long memberId) {
        // 获取会员基本信息
        SysMember member = getById(memberId);
        if (ObjectUtil.isNull(member)) {
            throw new BusinessException("会员不存在");
        }

        SysMemberInfoVO infoVO = new SysMemberInfoVO();
        BeanUtil.copyProperties(member, infoVO);
        
        // 填充租户名称
        if (member.getTenantId() != null) {
            SysTenant tenant = sysTenantService.getById(member.getTenantId());
            if (tenant != null) {
                infoVO.setTenantName(tenant.getName());
            }
        }

        //获取村小组信息
        PointVillageOrganizationBizVO organizeInfo = pointsBizService.organizeInfo(SecurityConstants.INNER);
        if (organizeInfo != null) {
            infoVO.setOrganizeId(organizeInfo.getId());
            infoVO.setOrganizeName(organizeInfo.getName());
        }

        // 获取实名认证信息
        SysMemberRealNameAuth authInfo = sysMemberRealNameAuthService.getByMemberId(memberId);
        if (authInfo != null) {
            // 填充实名认证详细信息到嵌套对象
            SysMemberInfoVO.RealNameInfo realNameInfo = new SysMemberInfoVO.RealNameInfo();
            realNameInfo.setRealName(authInfo.getRealName());
            realNameInfo.setPhone(authInfo.getPhone());
            realNameInfo.setGender(authInfo.getGender());
            realNameInfo.setBirthday(authInfo.getBirthday());
            realNameInfo.setAddress(authInfo.getAddress());
            infoVO.setRealNameInfo(realNameInfo);
        }
        
        return infoVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMemberInfo(SysMemberInfoUpdateDTO dto) {
        // 从当前登录用户获取会员ID
        Long memberId = SecurityUtils.getUserId();
        
        // 获取会员信息
        SysMember member = getById(memberId);
        if (ObjectUtil.isNull(member)) {
            throw new BusinessException("会员不存在");
        }

        // 更新会员基本信息（租户、性别、手机号）
        if (dto.getTenantId() != null) {
            member.setTenantId(dto.getTenantId());
        }
        if (dto.getGender() != null) {
            member.setSex(dto.getGender());  // 同步更新 sys_member 表的性别
        }
        if (StringUtils.isNotBlank(dto.getPhone())) {
            member.setPhone(dto.getPhone());  // 同步更新 sys_member 表的手机号
        }

        // 保存会员基本信息
        boolean memberUpdated = updateById(member);

        //保存村小组信息
        if (dto.getOrganizeId() != null) {
            PointOrganizeUserBindBIzDTO pointOrganizeUserBindBIzDTO = new PointOrganizeUserBindBIzDTO();
            pointOrganizeUserBindBIzDTO.setMemberId(memberId);
            pointOrganizeUserBindBIzDTO.setOrganizationId(dto.getOrganizeId());
            pointsBizService.organizeBind(pointOrganizeUserBindBIzDTO, SecurityConstants.INNER);
        }

        // 更新实名认证信息（性别、生日、手机号）
        SysMemberRealNameAuth authInfo = sysMemberRealNameAuthService.getByMemberId(memberId);
        
        // 判断是否需要更新实名认证信息
        if (dto.getGender() != null || dto.getBirthday() != null || StringUtils.isNotBlank(dto.getPhone())) {
            if (authInfo == null) {
                // 创建新记录
                authInfo = new SysMemberRealNameAuth();
                authInfo.setMemberId(memberId);
                authInfo.setAuthStatus(RealNameAuthStatusEnum.PENDING.getCode()); // 待审核
            }
            
            // 更新字段
            if (dto.getGender() != null) {
                authInfo.setGender(dto.getGender());
            }
            if (dto.getBirthday() != null) {
                authInfo.setBirthday(dto.getBirthday());
            }
            if (StringUtils.isNotBlank(dto.getPhone())) {
                authInfo.setPhone(dto.getPhone());
            }
            
            // 使用 saveOrUpdate 自动判断新增或更新
            sysMemberRealNameAuthService.saveOrUpdate(authInfo);
        }

        return memberUpdated;
    }
}
