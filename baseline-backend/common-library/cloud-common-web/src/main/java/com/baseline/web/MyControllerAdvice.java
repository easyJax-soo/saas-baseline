
package com.baseline.web;

import java.util.*;

import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.baseline.core.exception.ApiModuleException;
import com.baseline.core.exception.BusinessException;
import com.baseline.core.exception.InnerAuthException;
import com.baseline.core.exception.ServiceException;
import com.baseline.core.response.AjaxResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * 响应增强类，
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.baseline", "cn.dev33"})
public class MyControllerAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private WebProperties webProperties;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        // 在这里实现判断当前请求地址是否在配置的路由数组中
        // 如果在配置数组中，返回 false，不执行 beforeBodyWrite 方法；否则返回 true，执行 beforeBodyWrite 方法。
        if (!CollectionUtils.isEmpty(webProperties.getCustomResponseUrl())) {
            // 获取当前请求的路径
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String currentRequestPath = null;
            if (attributes != null) {
                currentRequestPath = attributes.getRequest().getRequestURI();
            }
            if (!StringUtils.isEmpty(currentRequestPath)) {
                for (String url : webProperties.getCustomResponseUrl()) {
                    if (Objects.equals(url, currentRequestPath) || (url.contains("/**") && currentRequestPath.startsWith(url.replace("/**", "")))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 判断是否是Feign调用的响应
        if (isFeignRequest(serverHttpRequest)) {
            if (body instanceof AjaxResult) {
                serverHttpResponse.setStatusCode(HttpStatus.valueOf(((AjaxResult) body).getStatus()));
            }
            return body; // 如果是Feign调用，直接返回原始响应
        }

        // 只对json类型进行封装
        if (mediaType.includes(MediaType.APPLICATION_JSON)) {
            if (body instanceof AjaxResult) {
                return body;
            }

            if(body instanceof Exception){
                String message = null;
                Throwable cause = ((Exception) body).getCause();
                if (cause != null) {
                    message = cause.getMessage();
                } else {
                    message = ((Exception) body).getMessage();
                }
                return AjaxResult.error(message);
            }

            return AjaxResult.success(body);
        } else {
            return body;
        }
    }

    private boolean isFeignRequest(ServerHttpRequest serverHttpRequest) {
        List<String> feignHeader = serverHttpRequest.getHeaders().get("from-source");
        return feignHeader != null && feignHeader.contains("inner");
    }


//    @ResponseBody
//    @ExceptionHandler(value = Exception.class)
//    public AjaxResult handle(Exception e) {
//        log.error(e.getMessage(), e);
//        return AjaxResult.error("系统异常");
//    }


    @ResponseBody
    @ExceptionHandler(value = IOException.class)
    public AjaxResult handle(IOException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error("系统IO错误");
    }

//    @ResponseBody
//    @ExceptionHandler(value = BusinessException.class)
//    public AjaxResult handle(BusinessException e) {
//        return AjaxResult.error(e.getMessage());
//    }

//    @ResponseBody
//    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
//    public AjaxResult handleValidException(MethodArgumentNotValidException e) {
//        BindingResult bindingResult = e.getBindingResult();
//        String message = null;
//        if (bindingResult.hasErrors()) {
//            FieldError fieldError = bindingResult.getFieldError();
//            if (fieldError != null) {
//                message = fieldError.getField() + fieldError.getDefaultMessage();
//            }
//        }
//        return AjaxResult.error(message);
//    }


    @ResponseBody
    @ExceptionHandler(value = NullPointerException.class)
    public AjaxResult handleValidException(NullPointerException e) {
        String message = "空指针异常";
        log.error(e.getMessage(), e);
        return AjaxResult.error(message);
    }


//    /**
//     * 权限码异常
//     */
//    @ExceptionHandler(NotPermissionException.class)
//    public AjaxResult handleNotPermissionException(NotPermissionException e, HttpServletRequest request)
//    {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',权限码校验失败'{}'", requestURI, e.getMessage());
//        return new AjaxResult(HttpStatus.FORBIDDEN.value(), "没有访问权限，请联系管理员授权");
//    }

//    /**
//     * 角色权限异常
//     */
//    @ExceptionHandler(NotRoleException.class)
//    public AjaxResult handleNotRoleException(NotRoleException e, HttpServletRequest request)
//    {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',角色权限校验失败'{}'", requestURI, e.getMessage());
////        return AjaxResult.error(HttpStatus.FORBIDDEN.toString(), "没有访问权限，请联系管理员授权");
//        return new AjaxResult(HttpStatus.FORBIDDEN.value(), "角色没有访问权限，请联系管理员授权");
//    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return new AjaxResult(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public AjaxResult handleServiceException(BusinessException e, HttpServletRequest request)
    {
        log.error("业务异常'{}'", e.getMessage());
//        Integer code = 500;
//        return StringUtils.isNotNull(code) ? AjaxResult.error(e.getMessage()) : AjaxResult.error(e.getMessage());
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 请求路径中缺少必需的路径变量
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public AjaxResult handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求路径中缺少必需的路径变量'{}',发生系统异常.", requestURI, e);
        return new AjaxResult(HttpStatus.NOT_FOUND.value(), String.format("请求路径中缺少必需的路径变量[%s]", e.getVariableName()));
    }

    /**
     * 请求参数类型不匹配
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public AjaxResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求参数类型不匹配'{}',发生系统异常.", requestURI, e);
        return new AjaxResult(HttpStatus.BAD_REQUEST.value(), String.format("请求参数类型不匹配，参数[%s]要求类型为：'%s'，但输入值为：'%s'", e.getName(), e.getRequiredType().getName(), e.getValue()));
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        
        // 检查异常链中是否包含BusinessException（处理被MyBatis等框架包装的业务异常）
        Throwable cause = e.getCause();
        while (cause != null) {
            if (cause instanceof BusinessException) {
                log.error("请求地址'{}',业务异常: {}", requestURI, cause.getMessage());
                return AjaxResult.error(cause.getMessage());
            }
            cause = cause.getCause();
        }
        
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

//    /**
//     * 自定义验证异常
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
//    {
//        log.error(e.getMessage(), e);
//        String message = e.getBindingResult().getFieldError().getDefaultMessage();
//        return AjaxResult.error(message);
//    }

    /**
     * 内部认证异常
     */
    @ExceptionHandler(InnerAuthException.class)
    public AjaxResult handleInnerAuthException(InnerAuthException e)
    {
        return new AjaxResult(HttpStatus.BAD_GATEWAY.value(), e.getMessage());
    }

//    /**
//     * 未登录异常
//     */
//    @ExceptionHandler(UnauthorizedException.class)
//        public Object handleUnauthorizedException(UnauthorizedException e)
//    {
//        log.error(e.getMessage(), e);
//        return new AjaxResult(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
//    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("请求数据格式错误'{}'", e.getMessage());
        return new AjaxResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "请求数据格式错误:" + e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public Object handleNotLoginException(NotLoginException e) {
        log.error("认证失败'{}'", e.getMessage());
        return new AjaxResult(HttpStatus.UNAUTHORIZED.value(), "认证失败：" + e.getMessage());
    }

    @ExceptionHandler(NotPermissionException.class)
    public Object handleNotPermissionException(NotPermissionException e) {
        log.error("权限不足'{}'", e.getMessage());
        return new AjaxResult(HttpStatus.FORBIDDEN.value(), "权限校验失败：" + e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Object handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
//        String errorMessage = violations.stream()
//                .map(ConstraintViolation::getMessage) // 只获取错误消息
//                .collect(Collectors.joining("; "));

        String errorMessage = violations.stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; ")); // 使用 Collectors.joining 进行拼接[6,7,8](@ref)
        return new AjaxResult(HttpStatus.BAD_REQUEST.value(), "参数检验失败：" + errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new AjaxResult(HttpStatus.BAD_REQUEST.value(), "请求参数检验失败：" + message);
    }


    @ExceptionHandler(DisableServiceException.class)
    public Object handleDisableServiceException(DisableServiceException ex) {
        return new AjaxResult(HttpStatus.FORBIDDEN.value(), "账号已经被禁用");
    }


    @ExceptionHandler(ServiceException.class)
    public Object handleServiceException(ServiceException ex) {
        return new AjaxResult(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }


    @ExceptionHandler(ApiModuleException.class)
    public Object handleApiModuleException(ApiModuleException ex) {
        return new AjaxResult(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }
}
