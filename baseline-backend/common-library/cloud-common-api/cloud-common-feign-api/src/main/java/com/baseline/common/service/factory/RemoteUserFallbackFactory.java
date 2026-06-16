package com.baseline.common.service.factory;

import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.dto.SysUserDetailBizDTO;
import com.baseline.common.dto.SysUserFilterBizDTO;
import com.baseline.common.service.ISysUserBizService;
import com.baseline.common.vo.*;
import com.baseline.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<ISysUserBizService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public ISysUserBizService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new ISysUserBizService()
        {
            @Override
            public LoginUserBizVO getLoginUserByUsername(LoginUserBizDTO dto, String source) {
                throw new BusinessException("登录用户获取失败:" + throwable.getMessage());
            }

            @Override
            public SysUserVO detail(SysUserDetailBizDTO dto, String source) {
                throw new BusinessException("用户详情获取失败:" + throwable.getMessage());
            }

            @Override
            public List<SysUserVO> simpleList(SysUserFilterBizDTO dto, String source) {
                log.error("用户列表获取失败:{}", throwable.getMessage());
                return Collections.emptyList();
            }
        };
    }
}
