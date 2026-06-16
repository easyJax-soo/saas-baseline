package com.baseline.common.security.handler;

import cn.dev33.satoken.SaManager;
import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.ObjectUtil;
import com.baseline.common.constant.CommonConstants;
import com.baseline.common.service.ISysProjectBizService;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.core.exception.ApiModuleException;
import com.baseline.core.exception.ServiceException;
import com.baseline.core.properties.IgnoreWhiteProperties;
import com.baseline.core.utils.StringUtils;
import com.baseline.utils.security.SaTokenUtils;
import com.baseline.utils.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 服务模块访问权限校验
 */
@Component
@Slf4j
public class ServiceCheckInterceptorHandler implements HandlerInterceptor {
    @Autowired
    private IgnoreWhiteProperties ignoreWhite;
    
    @Autowired
    @Lazy
    private ISysProjectBizService sysProjectBizService;

    // 用于路径模式匹配
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestPath = request.getRequestURI();

        String serviceName = request.getHeader(CommonConstants.HEADER_SERVICE_PREFIX);
        String apiModuleName = request.getHeader(CommonConstants.HEADER_API_PREFIX);

        if (isWhitelisted(requestPath, serviceName)) {
            // 在白名单中，直接放行，不进行后续权限校验
            return true;
        }

        // 租户切换接口单独放行，不校验服务权限
        if (requestPath != null && requestPath.contains(CommonConstants.SWITCH_TENANT_PATH)) {
            log.debug("租户切换接口放行，跳过服务权限校验: {}", requestPath);
            return true;
        }

        if(StringUtils.isNull(serviceName)){
            throw new ServiceException("serviceName未设置");
        }

        // feign调用不需要校验
        if(CommonConstants.FEIGN_API_PREFIX.equals(apiModuleName)){
            return true;
        }

        //auth服务不需要校验
        if(CommonConstants.AUTH_SERVICE.equals(serviceName)){
            return true;
        }

        LoginUserBizVO loginUser = null;
        try {
            loginUser = SecurityUtils.getLoginUser();
        } catch (Exception e) {
            // SaTokenContext 未初始化，说明用户未登录或会话失效
            throw new ServiceException("用户未登录或会话已失效");
        }

        if (loginUser == null) {
            throw new ServiceException("用户未登录");
        }

        //member登录暂时不校验
        if(loginUser.getLoginType().equals(SaTokenUtils.LOGIN_MEMBER)){
            return true;
        }

        // 校验服务访问权限（包含项目权限校验）
        if(!hasServiceAccess(serviceName, request)){
            throw new ServiceException("无权限访问服务[" + serviceName + "]");
        }

        //校验当前登录方式是否有权限访问接口
        if(CommonConstants.ADMIN_API_PREFIX.equals(apiModuleName) && !loginUser.getLoginType().equals(SaTokenUtils.LOGIN_ADMIN) ||
                CommonConstants.WEB_API_PREFIX.equals(apiModuleName) && !loginUser.getLoginType().equals(SaTokenUtils.LOGIN_MEMBER)
        ){
            throw new ApiModuleException("当前认证类型[" +loginUser.getLoginType()+ "]无法访问当前包含["+ apiModuleName +"]前缀的接口");
        }

        return true;
    }

    /**
     * 校验用户是否有权限访问指定服务
     * @param serviceName 服务名称
     * @param request HTTP请求
     * @return 是否有权限访问
     */
    private boolean hasServiceAccess(String serviceName, HttpServletRequest request) {
        try {
            // 获取用户有权限访问的项目编码列表
            List<String> userProjectCodes = getUserProjectCodes();
            
            // 根据项目权限获取用户可访问的服务列表
            List<String> allowedServices = getServicesByProjectCodes(userProjectCodes);
            
            // 检查请求的服务是否在允许列表中
            if (!allowedServices.contains(serviceName)) {
                log.warn("用户无权限访问服务[{}]，允许的服务列表: {}", serviceName, allowedServices);
                return false;
            }
            return true;
            
        } catch (Exception e) {
            log.error("服务权限校验失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    
    /**
     * 获取用户有权限访问的项目编码列表（带缓存）
     * 参考 StpInterfaceImplHandler.getPermissionList() 的缓存逻辑
     * @return 项目编码列表
     */
    @SuppressWarnings("unchecked")
    private List<String> getUserProjectCodes() {
        // 获取当前登录用户ID
        LoginUserBizVO loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null || loginUser.getId() == null) {
            return new ArrayList<>();
        }
        
        Long currentUserId = loginUser.getId();
        
        // 构建缓存键，与权限缓存保持一致的逻辑，只使用用户ID
        String cacheKey = CommonConstants.SATOKEN_PROJECT_CACHE_PREFIX + currentUserId;
        
        // 先尝试从缓存获取项目权限列表
        List<String> projectCodeList = (List<String>) SaManager.getSaTokenDao().getObject(cacheKey);

        if (projectCodeList == null) {
            try {
                // 缓存中没有，调用服务获取项目权限列表
                projectCodeList = sysProjectBizService.getUserProjectCodes("inner");
                
                // 将项目权限列表存入缓存
                if (ObjectUtil.isNotEmpty(projectCodeList)) {
                    SaManager.getSaTokenDao().setObject(cacheKey, projectCodeList, CommonConstants.SATOKEN_CACHE_EXPIRE);
                }
                
            } catch (Exception e) {
                // 如果出现异常（如服务调用失败），返回空项目列表
                log.error("获取用户项目权限失败: {}", e.getMessage(), e);
                projectCodeList = new ArrayList<>();
            }
        }
        
        return projectCodeList;
    }
    
    /**
     * 根据项目编码列表获取用户可访问的服务列表
     * @param projectCodes 项目编码列表
     * @return 服务列表
     */
    private List<String> getServicesByProjectCodes(List<String> projectCodes) {
        List<String> serviceList = new ArrayList<>();
        
        // 基础服务（所有用户都可以访问）
        serviceList.add(CommonConstants.AUTH_SERVICE);
        
        // 如果用户有项目权限，则可以访问system服务
        if (projectCodes != null && !projectCodes.isEmpty()) {
            serviceList.addAll(projectCodes);
        }
        
        // 去重
        return serviceList.stream().distinct().collect(Collectors.toList());
    }
    
    /**
     * 判断请求路径是否在白名单中
     */
    private boolean isWhitelisted(String requestPath, String serviceName) {
        List<String> whites = ignoreWhite.getWhites();
        if (whites == null || whites.isEmpty()) {
            return false;
        }

        log.debug("[白名单检查] 请求路径: {}, 服务名: {}", requestPath, serviceName);
        log.debug("[白名单检查] 白名单配置: {}", whites);

        // 使用AntPathMatcher进行模式匹配（支持通配符）
        for (String pattern : whites) {
            log.debug("[白名单检查] 检查模式: {}", pattern);
            
            // 1. 直接用完整的白名单模式匹配请求路径（适用于单体模式）
            if (pathMatcher.match(pattern, requestPath)) {
                log.debug("[白名单检查] 直接匹配成功: {} -> {}", pattern, requestPath);
                return true;
            }
            
            // 2. 微服务模式：如果白名单模式包含服务前缀，尝试去掉服务前缀后匹配
            if (StringUtils.isNotEmpty(serviceName)) {
                String servicePrefix = "/" + serviceName;
                
                // 如果白名单模式以服务前缀开头
                if (pattern.startsWith(servicePrefix)) {
                    String patternWithoutService = pattern.substring(servicePrefix.length());
                    
                    // 确保去掉前缀后不为空，且以/开头或为空
                    if (StringUtils.isEmpty(patternWithoutService) || patternWithoutService.startsWith("/")) {
                        // 微服务模式下，请求路径已经被网关去掉了服务前缀
                        // 所以直接用去掉服务前缀的模式匹配当前请求路径
                        if (pathMatcher.match(patternWithoutService, requestPath)) {
                            log.debug("[白名单检查] 微服务模式匹配成功: {} (去掉前缀: {}) -> {}", 
                                    pattern, patternWithoutService, requestPath);
                            return true;
                        }
                    }
                }
                
                // 3. 兼容性处理：如果请求路径包含服务前缀（可能是单体模式或特殊情况）
                if (requestPath.startsWith(servicePrefix)) {
                    String requestPathWithoutService = requestPath.substring(servicePrefix.length());
                    if (pathMatcher.match(pattern, servicePrefix + requestPathWithoutService)) {
                        log.debug("[白名单检查] 带服务前缀匹配成功: {} -> {}", pattern, requestPath);
                        return true;
                    }
                }
            }
        }

        log.debug("[白名单检查] 未匹配到任何白名单模式");
        return false;
    }
    
    
}