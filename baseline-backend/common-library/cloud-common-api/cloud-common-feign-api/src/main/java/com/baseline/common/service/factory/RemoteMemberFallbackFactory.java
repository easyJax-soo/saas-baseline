package com.baseline.common.service.factory;

import com.baseline.common.dto.LoginUserBizDTO;
import com.baseline.common.dto.SysMemberDetailBizDTO;
import com.baseline.common.dto.SysMemberFilterBizDTO;
import com.baseline.common.service.ISysMemberBizService;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.common.vo.SysMemberVO;
import com.baseline.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 会员服务降级处理
 *
 * @author system
 */
@Slf4j
@Component
public class RemoteMemberFallbackFactory implements FallbackFactory<ISysMemberBizService>
{
    @Override
    public ISysMemberBizService create(Throwable throwable)
    {
        log.error("会员服务调用失败:{}", throwable.getMessage());
        return new ISysMemberBizService()
        {
            @Override
            public LoginUserBizVO getLoginUserByUsername(LoginUserBizDTO dto, String source)
            {
                throw new BusinessException("会员登录用户获取失败:" + throwable.getMessage());
            }

            @Override
            public SysMemberVO detail(SysMemberDetailBizDTO dto, String source) {
                throw new BusinessException("会员详情获取失败:" + throwable.getMessage());
            }

            @Override
            public List<SysMemberVO> simpleList(SysMemberFilterBizDTO dto, String source) {
                log.error("会员列表获取失败:{}", throwable.getMessage());
                return Collections.emptyList();
            }
        };
    }
}
