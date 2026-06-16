package com.baseline.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.mybatis.config.TenantProperties;
import com.baseline.utils.security.SaTokenUtils;
import lombok.extern.slf4j.Slf4j;
import com.baseline.utils.security.SecurityUtils;
import com.baseline.utils.cache.PermissionCacheUtils;
import com.baseline.core.exception.BusinessException;
import com.baseline.system.dto.*;
import com.baseline.system.dto.SysUserDetailDTO;
import com.baseline.system.dto.UserTenantSwitchDTO;
import com.baseline.system.entity.*;
import com.baseline.system.entity.SysUserThirdPartyBind;
import com.baseline.system.enums.RealNameAuthStatusEnum;
import com.baseline.system.mapper.*;
import com.baseline.system.service.*;
import com.baseline.system.vo.*;
import com.baseline.system.vo.UserTenantVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private ISysUserRoleService userRoleService;

    @Autowired
    private ISysUserPostService userPostService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private ISysPermissionService sysPermissionService;

    @Autowired
    SysTenantUserMapper sysTenantUserMapper;

    @Autowired
    private ISysUserThirdPartyBindService sysUserThirdPartyBindService;

    @Autowired
    private ISysUserRealNameAuthService sysUserRealNameAuthService;

    @Autowired
    private TenantProperties tenantProperties;

    @Autowired
    private ISysTenantService sysTenantService;


    @Override
    public IPage<PageSysUserVO> pageUser(SysUserFilterDTO dto) {
        Page<PageSysUserVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        
        // 获取当前租户ID，用于查询逻辑判断
        Long currentTenantId = SecurityUtils.getTenantId();
        
        IPage<PageSysUserVO> result;
        if (currentTenantId != null && currentTenantId > 0) {
            // 在租户环境下，使用租户拦截器自动过滤的查询
            result = baseMapper.pageTenantUser(page, dto);
        } else {
            // 在非租户环境下，查询所有用户（包括租户用户和非租户用户）
            result = baseMapper.pageUser(page, dto);
        }
        
        // 填充第三方绑定信息和实名认证信息
        if (CollectionUtil.isNotEmpty(result.getRecords())) {
            List<Long> userIds = result.getRecords().stream()
                .map(PageSysUserVO::getId)
                .collect(Collectors.toList());
            
            // 查询这些用户的第三方绑定情况
            LambdaQueryWrapper<SysUserThirdPartyBind> bindQueryWrapper = new LambdaQueryWrapper<>();
            bindQueryWrapper.in(SysUserThirdPartyBind::getUserId, userIds);
            List<SysUserThirdPartyBind> bindList = sysUserThirdPartyBindService.list(bindQueryWrapper);
            
            // 构建用户ID到绑定状态的映射
            Set<Long> boundUserIds = bindList.stream()
                .map(SysUserThirdPartyBind::getUserId)
                .collect(Collectors.toSet());
            
            // 批量获取用户实名认证状态
            Map<Long, Integer> userAuthStatusMap = sysUserRealNameAuthService.batchGetUserAuthStatus(userIds);
            
            // 设置绑定状态和实名认证状态
            result.getRecords().forEach(user -> {
                user.setHasThirdPartyBind(boundUserIds.contains(user.getId()));
                
                // 设置实名认证状态
                Integer authStatus = userAuthStatusMap.get(user.getId());
                if (authStatus != null) {
                    user.setRealNameAuthStatus(authStatus);
                    // 只有认证通过时，hasRealNameAuth才为true
                    user.setHasRealNameAuth(authStatus.equals(RealNameAuthStatusEnum.APPROVED.getCode()));
                } else {
                    user.setHasRealNameAuth(false);
                    user.setRealNameAuthStatus(null);
                }
            });
        }
        
        return result;
    }

    @Override
    @Transactional
    public boolean saveOrUpdateUser(SysUserSaveDTO dto) {
        Long userId;
        SysUser user = new SysUser();
        BeanUtils.copyProperties(dto, user);

        if (lambdaQuery()
                .ne(dto.getId() != null, SysUser::getId, dto.getId())
                .eq(SysUser::getAccount, dto.getAccount())
                .exists()) {
            throw new BusinessException("用户账号已经存在");
        }
        
        if (dto.getId() == null) {
            // 新增用户
            passwordValidator(dto.getPassword());

            String salt = RandomUtil.randomString(6);
            user.setSalt(salt);
            user.setPassword(SecurityUtils.encryptPassword(dto.getPassword(), salt));
            save(user);
            userId = user.getId();
        } else {
            user.setUpdateTime(LocalDateTime.now());
            updateById(user);
            userRoleService.lambdaUpdate()
                    .eq(SysUserRole::getUserId, dto.getId())
                    .remove();
            userId = dto.getId();
        }


        // 用户角色关联
        userRoleService.lambdaUpdate()
                .eq(SysUserRole::getUserId, userId)
                .remove();
        if (!CollectionUtil.isEmpty(dto.getRoleIds())) {
            List<SysUserRole> userRoles = new ArrayList<>();
            for (Long roleId : dto.getRoleIds()) {
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                userRoles.add(userRole);
            }
            userRoleService.saveBatch(userRoles);
        }

        // 清除用户权限和角色缓存，确保权限变更立即生效
        PermissionCacheUtils.clearUserCache(userId);

        // 用户岗位关联
        userPostService.lambdaUpdate()
                .eq(SysUserPost::getUserId, userId)
                .remove();
        if (!CollectionUtil.isEmpty(dto.getPostIds())) {
            List<SysUserPost> userPosts = new ArrayList<>();
            for (Long postId : dto.getPostIds()) {
                SysUserPost userPost = new SysUserPost();
                userPost.setPostId(postId);
                userPost.setUserId(userId);
                userPosts.add(userPost);
            }
            userPostService.saveBatch(userPosts);
        }


        //绑定租户
        bindTenantUser(userId);
        return true;
    }

    /**
     * 绑定租户用户
     */
    private void bindTenantUser(Long userId){
        Long currentTenantId = SecurityUtils.getTenantId();
        if(ObjectUtil.isNotNull(SecurityUtils.getLoginUser()) && ObjectUtil.isNotEmpty(currentTenantId) && currentTenantId > 0){
            // 在租户环境下，检查是否已存在关联关系（租户拦截器会自动添加租户条件）
            LambdaQueryWrapper<SysTenantUser> query = new LambdaQueryWrapper<>();
            query.eq(SysTenantUser::getUserId, userId);
            SysTenantUser sysTenantUser = sysTenantUserMapper.selectOne(query);
            
            if(ObjectUtil.isNull(sysTenantUser)){
                // 创建租户用户关联关系
                SysTenantUser entity = new SysTenantUser();
                entity.setUserId(userId);
                entity.setTenantId(currentTenantId);
                entity.setIsTenantAdmin(false); // 默认不是租户管理员
                entity.setStatus(1); // 状态为正常
                sysTenantUserMapper.insert(entity);
            }
        }
    }

    @Override
    public SysUserDetailVO detail(SysUserDetailDTO dto) {
        SysUserDetailVO result = new SysUserDetailVO();
        
        // 获取当前租户ID，判断查询方式
        Long currentTenantId = SecurityUtils.getTenantId();
        SysUser user = null;
        
        if (currentTenantId != null && currentTenantId > 0) {
            // 在租户环境下，通过sys_tenant_user关联查询，租户拦截器自动添加租户条件
            user = baseMapper.selectTenantUserById(dto.getId());
        } else {
            // 在非租户环境下，直接查询sys_user
            user = lambdaQuery()
                    .eq(SysUser::getId, dto.getId())
                    .eq(SysUser::getDeleted, false)
                    .one();
        }
        
        if (user != null) {
            BeanUtils.copyProperties(user, result);
            
            // 查询部门名称
            if (user.getDeptId() != null) {
                SysDept dept = sysDeptMapper.selectById(user.getDeptId());
                if (dept != null) {
                    result.setDeptName(dept.getName());
                }
            }
            
            List<SysUserRole> userRoles = userRoleService.lambdaQuery()
                    .eq(SysUserRole::getUserId, dto.getId())
                    .list();
            if (!CollectionUtil.isEmpty(userRoles)) {
                result.setRoleIds(userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
            }

            List<SysUserPost> userPosts = userPostService.lambdaQuery()
                    .eq(SysUserPost::getUserId, dto.getId())
                    .list();

            if (!CollectionUtil.isEmpty(userPosts)) {
                result.setPostIds(userPosts.stream().map(SysUserPost::getPostId).collect(Collectors.toList()));
            }

            // 查询用户可访问的租户列表
            List<UserTenantVO> accessibleTenants = sysTenantUserMapper.getUserTenantList(dto.getId());
            result.setAccessibleTenants(accessibleTenants);
            
            // 查询第三方绑定状态
            LambdaQueryWrapper<SysUserThirdPartyBind> bindQueryWrapper = new LambdaQueryWrapper<>();
            bindQueryWrapper.eq(SysUserThirdPartyBind::getUserId, dto.getId());
            boolean hasThirdPartyBind = sysUserThirdPartyBindService.exists(bindQueryWrapper);
            result.setHasThirdPartyBind(hasThirdPartyBind);
            
            // 查询实名认证状态
            Map<Long, Integer> authStatusMap = sysUserRealNameAuthService.batchGetUserAuthStatus(Collections.singletonList(dto.getId()));
            Integer authStatus = authStatusMap.get(dto.getId());
            if (authStatus != null) {
                result.setRealNameAuthStatus(authStatus);
                // 只有认证通过时，hasRealNameAuth才为true
                result.setHasRealNameAuth(authStatus.equals(RealNameAuthStatusEnum.APPROVED.getCode()));
            } else {
                result.setHasRealNameAuth(false);
                result.setRealNameAuthStatus(null);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public boolean removeSysRole(List<Long> ids) {
        // 先清除这些用户的缓存
        PermissionCacheUtils.clearUserCacheBatch(ids);
        
        Long currentTenantId = SecurityUtils.getTenantId();
        if (currentTenantId != null && currentTenantId > 0) {
            // 在租户环境下，只删除租户用户关联关系（租户拦截器会自动添加租户条件）
            sysTenantUserMapper.delete(
                new LambdaQueryWrapper<SysTenantUser>()
                    .in(SysTenantUser::getUserId, ids)
            );
            
            // 删除租户环境下的用户角色和岗位关联（这些表如果有租户字段，拦截器也会自动处理）
            userRoleService.lambdaUpdate()
                    .in(SysUserRole::getUserId, ids)
                    .remove();

            userPostService.lambdaUpdate()
                    .in(SysUserPost::getUserId, ids)
                    .remove();
        } else {
            // 在非租户环境下，逻辑删除用户（软删除）
            lambdaUpdate()
                    .in(SysUser::getId, ids)
                    .eq(SysUser::getDeleted, false)
                    .set(SysUser::getDeleted, 1)
                    .update();
            userRoleService.lambdaUpdate()
                    .in(SysUserRole::getUserId, ids)
                    .remove();

            userPostService.lambdaUpdate()
                    .in(SysUserPost::getUserId, ids)
                    .remove();
        }
        return true;
    }

    @Override
    public boolean setUserRole(UserRoleSaveDTO dto) {
        userRoleService.lambdaUpdate()
                .eq(SysUserRole::getUserId, dto.getId())
                .remove();
        if (!CollectionUtil.isEmpty(dto.getRoleIds())) {
            List<SysUserRole> userRoles = new ArrayList<>();
            for (Long roleId : dto.getRoleIds()) {
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(dto.getId());
                userRoles.add(userRole);
            }
            userRoleService.saveBatch(userRoles);
        }
        
        // 清除用户权限和角色缓存
        PermissionCacheUtils.clearUserCache(dto.getId());
        
        return true;
    }


    @Override
    public MyUserDetailVO getMyDetail() {
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            throw new BusinessException("用户未登录");
        }
        
        MyUserDetailVO vo = new MyUserDetailVO();
        
        // 使用不受租户拦截器影响的查询方法
        SysUser user = lambdaQuery()
                .eq(SysUser::getId, userId)
                .eq(SysUser::getDeleted, false)
                .one();
                
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        BeanUtils.copyProperties(user, vo);
        List<SimpleRoleVO> roles = sysRoleService.getSimpleListByUserId(userId);
        vo.setRoles(roles);
        
        // 获取当前租户ID
        Long currentTenantId = SecurityUtils.getTenantId();
        vo.setCurrentTenantId(currentTenantId);
        
        // 获取用户可访问的租户列表（包含isCurrent标识）
        List<UserTenantVO> accessibleTenants = getUserTenantList();
        vo.setAccessibleTenants(accessibleTenants);
        
        // 设置当前租户名称
        if (currentTenantId != null && currentTenantId > 0) {
            accessibleTenants.stream()
                .filter(tenant -> tenant.getTenantId().equals(currentTenantId))
                .findFirst()
                .ifPresent(tenant -> vo.setCurrentTenantName(tenant.getTenantName()));
        }
        
        // 查询第三方绑定状态
        LambdaQueryWrapper<SysUserThirdPartyBind> bindQueryWrapper = new LambdaQueryWrapper<>();
        bindQueryWrapper.eq(SysUserThirdPartyBind::getUserId, userId);
        boolean hasThirdPartyBind = sysUserThirdPartyBindService.exists(bindQueryWrapper);
        vo.setHasThirdPartyBind(hasThirdPartyBind);
        
        // 查询实名认证状态
        Map<Long, Integer> authStatusMap = sysUserRealNameAuthService.batchGetUserAuthStatus(Collections.singletonList(userId));
        Integer authStatus = authStatusMap.get(userId);
        if (authStatus != null) {
            vo.setRealNameAuthStatus(authStatus);
            // 只有认证通过时，hasRealNameAuth才为true
            vo.setHasRealNameAuth(authStatus.equals(RealNameAuthStatusEnum.APPROVED.getCode()));
        } else {
            vo.setHasRealNameAuth(false);
            vo.setRealNameAuthStatus(null);
        }
        
        return vo;
    }

    @Override
    public boolean setMyUserInfo(UserInfoDTO dto) {
        SysUser user = BeanUtil.copyProperties(dto, SysUser.class);
        user.setId(SecurityUtils.getUserId());
        int result = baseMapper.updateById(user);
        return result > 0;
    }

    @Override
    public boolean changePw(SysUserChangePasswordDTO dto) {
        passwordValidator(dto.getNewPw());

        SysUser user = lambdaQuery()
                .eq(SysUser::getId, SecurityUtils.getUserId())
                .eq(SysUser::getDeleted, 0)
                .one();

        if(!SecurityUtils.matchesPassword(dto.getOldPw(), user.getPassword(), user.getSalt())){
            throw new BusinessException("旧密码不正确");
        }

        user.setInitializePasswordChange(true);
        String salt = RandomUtil.randomString(6);
        user.setSalt(salt);
        user.setPassword(SecurityUtils.encryptPassword(dto.getNewPw(), salt));
        return updateById(user);
    }

    @Override
    public boolean resetPw(SysUserResetPasswordDTO dto) {
        passwordValidator(dto.getNewPw());

        SysUser user = lambdaQuery()
                .eq(SysUser::getId, dto.getUserId())
                .eq(SysUser::getDeleted, 0)
                .one();
//        user.setPassword(SecureUtil.md5(newPw + user.getSalt()));
        String salt = RandomUtil.randomString(6);
        user.setSalt(salt);
        user.setPassword(SecurityUtils.encryptPassword(dto.getNewPw(), salt));
        return updateById(user);
    }

    @Override
    public List<SysUserVO> getSimpleList(SysUserFilterDTO dto) {
        if (dto.getRoleId() != null) {
            List<SysUserRole> userRoles = userRoleService.lambdaQuery()
                    .eq(SysUserRole::getRoleId, dto.getRoleId())
                    .list();
            if (!CollectionUtil.isEmpty(userRoles)) {
                dto.setIds(userRoles.stream().map(SysUserRole::getUserId).collect(Collectors.toList()));
            }
        }
        return baseMapper.getSimpleList(dto);
    }


    @Override
    public LoginUserBizVO getLoginUserByUsername(LoginUserBizDTO dto) {
        // 使用专门的登录查询方法，忽略租户拦截器
        SysUser user = baseMapper.selectUserForLogin(dto.getUsername());

        if(ObjectUtil.isNull(user)){
            throw new BusinessException("用户不存在");
        }

        LoginUserBizVO loginUser = BeanUtil.copyProperties(user, LoginUserBizVO.class);
        loginUser.setUsername(user.getAccount());
        loginUser.setPassword(user.getPassword()); // 设置密码用于认证
        loginUser.setSalt(user.getSalt()); // 设置盐值用于认证
        loginUser.setLoginType(SaTokenUtils.LOGIN_ADMIN);

        try {
            //查询用户角色
            List<SysRole> roleList = sysRoleService.selectUserRoleByUserId(user.getId());
            List<Long> roleIds = roleList.stream().map(SysRole::getId).collect(Collectors.toList());
            List<String> roleKeys = roleList.stream().map(SysRole::getKey).collect(Collectors.toList());

            //查询用户菜单
            List<String> menus = sysMenuService.selectMenuPermsByUserId(user.getId());

            //查询权限
            List<String> permission = sysPermissionService.getPermissionByUserId(user.getId());

            //查询部门列表
            SysDept dept = null;
            if (user.getDeptId() != null) {
                dept = sysDeptMapper.selectById(user.getDeptId());
            }
            List<Long> deptIds = new ArrayList<>();
            List<String> deptCodes = new ArrayList<>();
            if(ObjectUtil.isNotNull(dept)){
                deptIds.add(dept.getId());
                deptCodes.add(dept.getCode());
            }
            
            loginUser.setRoleIds(roleIds != null ? roleIds : new ArrayList<>());
            loginUser.setRoles(roleKeys != null ? roleKeys : new ArrayList<>());
            loginUser.setMenus(menus != null ? menus : new ArrayList<>());
            loginUser.setPermissions(permission != null ? permission : new ArrayList<>());
            loginUser.setDeptIds(deptIds != null ? deptIds : new ArrayList<>());
            loginUser.setDeptCodes(deptCodes != null ? deptCodes : new ArrayList<>());
            loginUser.setTenantId(0L);

        } catch (Exception e) {
            // 如果权限查询失败，记录日志但不影响登录
            // 权限信息可以在租户切换时重新加载
            // 设置默认空值，避免返回null
            loginUser.setRoleIds(new ArrayList<>());
            loginUser.setRoles(new ArrayList<>());
            loginUser.setMenus(new ArrayList<>());
            loginUser.setPermissions(new ArrayList<>());
            loginUser.setDeptIds(new ArrayList<>());
            loginUser.setDeptCodes(new ArrayList<>());
            loginUser.setTenantId(0L);
        }

        return loginUser;
    }

    /**
     * 密码强度验证
     * @param password
     * @return
     */
    public void passwordValidator(String password){
        SecurityUtils.passwordValidator(password);
    }

    @Override
    public List<UserTenantVO> getUserTenantList() {
        // 如果没有开启租户配置，返回空列表
        if (tenantProperties == null || !tenantProperties.getEnable()) {
            return new ArrayList<>();
        }
        
        Long userId = SecurityUtils.getUserId();
        List<UserTenantVO> tenantList = sysTenantUserMapper.getUserTenantList(userId);
        
        // 获取当前用户的租户ID
        Long currentTenantId = SecurityUtils.getTenantId();
        
        // 设置当前租户标识
        tenantList.forEach(tenant -> {
            tenant.setIsCurrent(tenant.getTenantId().equals(currentTenantId));
        });

        // 添加"非租户模式"选项
        UserTenantVO nonTenantOption = new UserTenantVO();
        nonTenantOption.setTenantId(0L);
        nonTenantOption.setTenantName("非租户模式");
        nonTenantOption.setTenantCode("NON_TENANT");
        nonTenantOption.setIsCurrent(currentTenantId == null || currentTenantId == 0L);
        nonTenantOption.setIsTenantAdmin(false);
        nonTenantOption.setTenantStatus(1);
        
        // 将非租户选项添加到列表开头
        tenantList.add(0, nonTenantOption);

        return tenantList;
    }

    @Override
    public boolean switchTenant(UserTenantSwitchDTO dto) {
        // 如果没有开启租户配置，不允许切换租户
        if (tenantProperties == null || !tenantProperties.getEnable()) {
            throw new BusinessException("租户功能未启用");
        }
        
        if (ObjectUtil.isEmpty(dto.getTenantId())) {
            throw new BusinessException("目标租户ID不能为空");
        }
        
        // 检查用户是否有权限访问目标租户
        List<UserTenantVO> userTenants = getUserTenantList();
        UserTenantVO targetTenant = userTenants.stream()
            .filter(tenant -> tenant.getTenantId().equals(dto.getTenantId()) 
                && (tenant.getTenantStatus() == 1 || dto.getTenantId() == 0L)) // 允许切换到非租户模式(tenantId=0)
            .findFirst()
            .orElse(null);
        
        if (targetTenant == null) {
            throw new BusinessException("您没有权限访问该租户");
        }
        
        // 获取当前用户信息
        Long userId = SecurityUtils.getUserId();
        SysUser currentUser = lambdaQuery()
                .eq(SysUser::getId, userId)
                .eq(SysUser::getDeleted, false)
                .one();
                
        if (currentUser == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 更新session中的租户信息
        Map<String, Object> currentUserMap = SaTokenUtils.ADMIN.getSession().getDataMap();
        currentUserMap.put("tenantId", dto.getTenantId());
        
        // 设置租户管理员状态（如果是切换到非租户模式，则设置为false）
        Boolean isTenantAdmin = dto.getTenantId() == 0L ? false : targetTenant.getIsTenantAdmin();
        currentUserMap.put("isTenantAdmin", isTenantAdmin);
        
        // 设置层级租户ID列表（如果启用了层级租户功能）
        List<Long> hierarchyTenantIds = calculateHierarchyTenantIds(dto.getTenantId());
        if (hierarchyTenantIds != null) {
            currentUserMap.put("hierarchyTenantIds", hierarchyTenantIds);
            log.info("统一设置层级租户ID到currentUserMap: {}", hierarchyTenantIds);
        }
        
        SaTokenUtils.ADMIN.getSession().refreshDataMap(currentUserMap);
        
        // 清除权限、角色和项目权限相关的缓存，确保切换立即生效
        try {
            // 由于项目权限缓存与权限缓存保持一致的逻辑（只使用用户ID），
            // 所以只需要清除用户的所有权限缓存即可
            PermissionCacheUtils.clearUserCache(userId);
            
        } catch (Exception e) {
            // 忽略清除缓存的异常
        }
        
        return true;
    }

    /**
     * 计算层级租户ID列表
     * 在租户切换时调用，预计算当前租户及其所有子租户ID
     */
    private List<Long> calculateHierarchyTenantIds(Long tenantId) {
        log.info("开始计算层级租户ID，租户ID: {}", tenantId);
        
        // 检查是否启用层级租户
        boolean hierarchyTenantEnabled = tenantProperties != null && 
                                       tenantProperties.getHierarchy() != null && 
                                       Boolean.TRUE.equals(tenantProperties.getHierarchy().getEnable());
        
        log.info("层级租户配置检查 - tenantProperties: {}, hierarchy: {}, enabled: {}", 
                tenantProperties != null, 
                tenantProperties != null ? tenantProperties.getHierarchy() != null : false,
                hierarchyTenantEnabled);
        
        if (!hierarchyTenantEnabled || tenantId == null || tenantId <= 0) {
            log.warn("层级租户未启用或租户ID无效，返回null。enabled: {}, tenantId: {}", hierarchyTenantEnabled, tenantId);
            return null;
        }

        try {
            // 直接调用本地租户服务获取层级租户ID
            List<Long> hierarchyTenantIds = sysTenantService.getTenantAndChildrenIds(tenantId);
            log.info("获取到层级租户ID列表: {}", hierarchyTenantIds);
            
            if (CollectionUtil.isNotEmpty(hierarchyTenantIds)) {
                log.info("返回层级租户ID列表: {}", hierarchyTenantIds);
                return hierarchyTenantIds;
            } else {
                // 如果没有子租户，至少包含当前租户
                List<Long> fallbackIds = Arrays.asList(tenantId);
                log.info("没有子租户，返回当前租户ID: {}", fallbackIds);
                return fallbackIds;
            }
        } catch (Exception e) {
            log.error("计算层级租户ID异常，租户ID: {}, 错误: {}", tenantId, e.getMessage(), e);
            // 异常时降级：只使用当前租户ID
            List<Long> fallbackIds = Arrays.asList(tenantId);
            log.info("异常降级，返回当前租户ID: {}", fallbackIds);
            return fallbackIds;
        }
    }
}
