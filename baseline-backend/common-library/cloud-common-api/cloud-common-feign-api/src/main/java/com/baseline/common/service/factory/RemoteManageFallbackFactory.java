package com.baseline.common.service.factory;

import com.baseline.common.service.IManageBizService;
import com.baseline.common.vo.CountryUnifiedZoningVO;
import com.baseline.common.vo.NationalityInfoVO;
import com.baseline.common.vo.SiHuiZoningInfoVO;
import com.baseline.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 管理服务降级工厂
 *
 * @author cascade
 * @date 2025/11/15
 */
@Component
public class RemoteManageFallbackFactory implements FallbackFactory<IManageBizService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteManageFallbackFactory.class);

    @Override
    public IManageBizService create(Throwable cause) {
        log.error("管理服务调用失败:{}", cause.getMessage());
        return new IManageBizService() {
            @Override
            public CountryUnifiedZoningVO detail(Long id, String source) {
                throw new BusinessException(String.format("获取区域信息失败:%s", cause.getMessage()));
            }

            @Override
            public List<CountryUnifiedZoningVO> getSiHuiZoningDataByIds(Set<Long> ids, String source) {
                throw new BusinessException(String.format("批量获取区域信息失败:%s", cause.getMessage()));
            }

            @Override
            public List<SiHuiZoningInfoVO> getSiHuiZoningData(String source) {
                throw new BusinessException(String.format("获取四会区域数据失败:%s", cause.getMessage()));
            }

            @Override
            public List<NationalityInfoVO> getNationalityInfo(String source) {
                throw new BusinessException(String.format("获取民族信息失败:%s", cause.getMessage()));
            }

            @Override
            public String getThirdAreaName(String source, Integer level, Long areaId) {
                throw new BusinessException(String.format("获取三级地区名称失败:%s", cause.getMessage()));
            }

            @Override
            public String getThirdAreaId(String source, Integer level, String areaCode) {
                throw new BusinessException(String.format("获取三级地区ID失败:%s", cause.getMessage()));
            }

            @Override
            public List<SiHuiZoningInfoVO> getSiHuiZoningDataByAreaCodes(String source, List<String> areaCodes) {
                throw new BusinessException(String.format("根据区域编码获取四会区域数据失败:%s", cause.getMessage()));
            }
        };
    }
}
