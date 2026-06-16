package com.baseline.common.service.impl;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.service.IManageBizService;
import com.baseline.common.service.factory.RemoteManageFallbackFactory;
import com.baseline.common.vo.CountryUnifiedZoningVO;
import com.baseline.common.vo.NationalityInfoVO;
import com.baseline.common.vo.SiHuiZoningInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * 管理服务业务接口 Feign 客户端实现
 *
 * @author cascade
 * @date 2025/11/15
 */
@FeignClient(contextId = "manageBizService", value = "manage-service", fallbackFactory = RemoteManageFallbackFactory.class)
public interface ManageBizServiceImpl extends IManageBizService {

    @Override
    @GetMapping("/feignApi/base/countryUnifiedZoning/detail/{id}")
    CountryUnifiedZoningVO detail(@PathVariable("id") Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @PostMapping("/feignApi/base/countryUnifiedZoning/getSiHuiZoningDataByIds")
    List<CountryUnifiedZoningVO> getSiHuiZoningDataByIds(@RequestBody Set<Long> ids, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @GetMapping("/feignApi/base/countryUnifiedZoning/getSiHuiZoningData")
    List<SiHuiZoningInfoVO> getSiHuiZoningData(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @GetMapping("/feignApi/base/info/getNationalityInfo")
    List<NationalityInfoVO> getNationalityInfo(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @GetMapping("/feignApi/base/countryUnifiedZoning/getThirdAreaName")
    String getThirdAreaName(@RequestHeader(SecurityConstants.FROM_SOURCE) String source, @RequestParam("level") Integer level, @RequestParam("areaId") Long areaId);

    @Override
    @GetMapping("/feignApi/base/countryUnifiedZoning/getThirdAreaId")
    String getThirdAreaId(@RequestHeader(SecurityConstants.FROM_SOURCE) String source, @RequestParam("level") Integer level, @RequestParam("areaCode") String areaCode);

    @Override
    @PostMapping("/feignApi/base/countryUnifiedZoning/getSiHuiZoningDataByAreaCodes")
    List<SiHuiZoningInfoVO> getSiHuiZoningDataByAreaCodes(@RequestHeader(SecurityConstants.FROM_SOURCE) String source, @RequestBody List<String> areaCodes);
}
