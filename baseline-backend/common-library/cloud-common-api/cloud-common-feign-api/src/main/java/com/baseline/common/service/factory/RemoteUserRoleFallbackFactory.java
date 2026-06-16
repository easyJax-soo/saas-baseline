package com.baseline.common.service.factory;

import com.baseline.common.dto.UserRoleBizDTO;
import com.baseline.common.service.ISysUserRoleBizService;
import com.baseline.common.vo.UserRoleBizVO;
import com.baseline.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteUserRoleFallbackFactory implements FallbackFactory<ISysUserRoleBizService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteUserRoleFallbackFactory.class);

    @Override
    public ISysUserRoleBizService create(Throwable throwable)
    {
        log.error("用户角色服务调用失败:{}", throwable.getMessage());
        return new ISysUserRoleBizService()
        {
            @Override
            public List<UserRoleBizVO> getRolesByUserId(UserRoleBizDTO dto, String source) {
                throw new BusinessException("用户角色获取失败:" + throwable.getMessage());
            }
        };
    }
}
