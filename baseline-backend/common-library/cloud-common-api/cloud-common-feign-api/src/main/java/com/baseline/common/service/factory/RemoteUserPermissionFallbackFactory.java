package com.baseline.common.service.factory;

import com.baseline.common.dto.UserPermissionBizDTO;
import com.baseline.common.service.ISysUserPermissionBizService;
import com.baseline.common.vo.UserPermissionBizVO;
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
public class RemoteUserPermissionFallbackFactory implements FallbackFactory<ISysUserPermissionBizService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteUserPermissionFallbackFactory.class);

    @Override
    public ISysUserPermissionBizService create(Throwable throwable)
    {
        log.error("用户角色权限服务调用失败:{}", throwable.getMessage());
        return new ISysUserPermissionBizService()
        {

            @Override
            public List<UserPermissionBizVO> getPermissionsByUserIdAndRoleId(UserPermissionBizDTO dto, String source) {
                throw new BusinessException("用户角色权限获取失败:" + throwable.getMessage());
            }
        };
    }
}
