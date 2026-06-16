package com.baseline.common.service;

import com.baseline.common.vo.CountryUnifiedZoningVO;
import com.baseline.common.vo.NationalityInfoVO;
import com.baseline.common.vo.SiHuiZoningInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 管理服务业务接口
 *
 * @author cascade
 * @date 2025/11/15
 */
@Service
public interface IManageBizService {

    /**
     * 获取区域详情
     *
     * @param id 区域ID
     * @param source 来源标识
     * @return 区域信息
     */
    CountryUnifiedZoningVO detail(Long id, String source);

    /**
     * 批量获取四会区域数据
     *
     * @param ids 区域ID集合
     * @param source 来源标识
     * @return 区域信息列表
     */
    List<CountryUnifiedZoningVO> getSiHuiZoningDataByIds(Set<Long> ids, String source);

    /**
     * 获取四会区域数据
     *
     * @param source 来源标识
     * @return 四会区域信息列表
     */
    List<SiHuiZoningInfoVO> getSiHuiZoningData(String source);

    /**
     * 获取民族信息
     *
     * @param source 来源标识
     * @return 民族信息列表
     */
    List<NationalityInfoVO> getNationalityInfo(String source);

    /**
     * 获取三级内地区名称
     *
     * @param source 来源标识
     * @param level 级别
     * @param areaId 区域ID
     * @return 地区名称
     */
    String getThirdAreaName(String source, Integer level, Long areaId);

    /**
     * 获取三级内地区ID
     *
     * @param source 来源标识
     * @param level 级别
     * @param areaCode 区域编码
     * @return 地区ID
     */
    String getThirdAreaId(String source, Integer level, String areaCode);

    /**
     * 根据区域编码批量获取四会区域数据
     *
     * @param source 来源标识
     * @param areaCodes 区域编码列表
     * @return 四会区域信息列表
     */
    List<SiHuiZoningInfoVO> getSiHuiZoningDataByAreaCodes(String source, List<String> areaCodes);
}
