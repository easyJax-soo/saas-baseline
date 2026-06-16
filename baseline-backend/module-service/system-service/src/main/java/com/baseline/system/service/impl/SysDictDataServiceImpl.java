package com.baseline.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.common.constant.CommonConstants;
import com.baseline.common.dto.DictBizDTO;
import com.baseline.common.vo.DictBizVO;
import com.baseline.redis.util.RedisUtil;
import com.baseline.system.dto.SysDictDataDTO;
import com.baseline.system.dto.SysDictDataFilterDTO;
import com.baseline.system.entity.SysDictData;
import com.baseline.system.mapper.SysDictDataMapper;
import com.baseline.system.service.ISysDictDataService;
import com.baseline.system.vo.SysDictVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<DictBizVO> getDictDataByDictCode(DictBizDTO dto) {
        // 构建缓存key
        String cacheKey = CommonConstants.DICT_DATA_CACHE_PREFIX + dto.getCode();
        
        // 先从缓存中获取
        Object cacheData = redisUtil.get(cacheKey);
        if (cacheData != null) {
            try {
                // 尝试直接转换
                @SuppressWarnings("unchecked")
                List<DictBizVO> cachedResult = (List<DictBizVO>) cacheData;
                return cachedResult;
            } catch (ClassCastException e) {
                // 如果直接转换失败，说明是JSONObject类型，需要转换
                if (cacheData instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Object> objList = (List<Object>) cacheData;
                    List<DictBizVO> result = new ArrayList<>();
                    for (Object obj : objList) {
                        if (obj instanceof com.alibaba.fastjson2.JSONObject) {
                            com.alibaba.fastjson2.JSONObject jsonObj = (com.alibaba.fastjson2.JSONObject) obj;
                            DictBizVO dictVO = jsonObj.toJavaObject(DictBizVO.class);
                            result.add(dictVO);
                        }
                    }
                    return result;
                }
                // 如果转换失败，清除缓存并从数据库查询
                redisUtil.del(cacheKey);
            }
        }
        
        // 缓存中没有，从数据库查询
        List<DictBizVO> result = baseMapper.getDictDataByDictCode(dto);
        
        // 将查询结果存入缓存
        if (result != null && !result.isEmpty()) {
            redisUtil.set(cacheKey, result, CommonConstants.DICT_DATA_CACHE_EXPIRE);
        }
        
        return result;
    }

    @Override
    public boolean saveOrUpdateWithCache(SysDictDataDTO dto) {
        SysDictData data = new SysDictData();
        BeanUtils.copyProperties(dto, data);
        // setValue方法会自动清除空格
        boolean result = saveOrUpdate(data);
        
        // 清除对应的缓存
        if (result && dto.getCode() != null) {
            String cacheKey = CommonConstants.DICT_DATA_CACHE_PREFIX + dto.getCode();
            redisUtil.del(cacheKey);
        }
        
        return result;
    }

    @Override
    public boolean removeBatchByIdsWithCache(List<Long> ids) {
        // 先获取要删除的字典数据，以便清除缓存
        List<SysDictData> dictDataList = listByIds(ids);
        
        boolean result = removeBatchByIds(ids);
        
        // 删除成功后清除相关缓存
        if (result && dictDataList != null) {
            for (SysDictData dictData : dictDataList) {
                if (dictData.getCode() != null) {
                    String cacheKey = CommonConstants.DICT_DATA_CACHE_PREFIX + dictData.getCode();
                    redisUtil.del(cacheKey);
                }
            }
        }
        
        return result;
    }


    @Override
    public IPage<SysDictVO> dataPage(SysDictDataFilterDTO dto) {
        Page<SysDictData> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<SysDictData> dataPage = lambdaQuery().eq(StringUtils.isNotBlank(dto.getCode()), SysDictData::getCode, dto.getCode())
                .page(page);
        
        // 转换为VO
        Page<SysDictVO> voPage = new Page<>(dataPage.getCurrent(), dataPage.getSize(), dataPage.getTotal());
        List<SysDictVO> voList = new ArrayList<>();
        for (SysDictData data : dataPage.getRecords()) {
            SysDictVO vo = new SysDictVO();
            BeanUtils.copyProperties(data, vo);
            voList.add(vo);
        }
        voPage.setRecords(voList);
        return voPage;
    }

}
