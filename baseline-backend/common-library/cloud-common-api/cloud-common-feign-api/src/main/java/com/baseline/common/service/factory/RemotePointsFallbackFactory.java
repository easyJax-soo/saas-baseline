package com.baseline.common.service.factory;

import com.baseline.common.dto.HandlePointsDTO;
import com.baseline.common.dto.PointOrganizeUserBindBIzDTO;
import com.baseline.common.dto.SysUserFilterBizDTO;
import com.baseline.common.service.IPointsBizService;
import com.baseline.common.vo.*;
import com.baseline.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 积分服务降级工厂
 *
 * @author cascade
 * @date 2025/11/15
 */
@Component
public class RemotePointsFallbackFactory implements FallbackFactory<IPointsBizService> {
    private static final Logger log = LoggerFactory.getLogger(RemotePointsFallbackFactory.class);

    @Override
    public IPointsBizService create(Throwable cause) {
        log.error("积分服务调用失败:{}", cause.getMessage());
        return new IPointsBizService() {
            @Override
            public SysUserSaveVO getUserDetail(Long id, String source) {
                throw new BusinessException(String.format("获取用户信息失败:%s", cause.getMessage()));
            }

            @Override
            public List<SysUserVO> getSimpleList(SysUserFilterBizDTO dto, String source) {
                throw new BusinessException(String.format("获取用户列表失败:%s", cause.getMessage()));
            }

            @Override
            public boolean organizeBind(PointOrganizeUserBindBIzDTO dto, String source) {
                throw new BusinessException(String.format("绑定组织失败:%s", cause.getMessage()));
            }

            @Override
            public PointVillageOrganizationBizVO organizeInfo(String source) {
                throw new BusinessException(String.format("获取组织信息失败:%s", cause.getMessage()));
            }

            @Override
            public Boolean deductPoints(HandlePointsDTO dto, String source) {
                throw new BusinessException(String.format("扣减积分失败:%s", cause.getMessage()));
            }

            @Override
            public List<SystemUserPointsTopVO> pointsTop(String source) {
                throw new BusinessException(String.format("获取积分排行榜失败:%s", cause.getMessage()));
            }

            @Override
            public SystemUserPointsVO userPoints(Long id, String source) {
                throw new BusinessException(String.format("获取用户积分失败:%s", cause.getMessage()));
            }
        };
    }
}
