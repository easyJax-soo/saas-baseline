package com.baseline.system.service.impl;

import cn.hutool.json.JSONUtil;
import com.baseline.core.exception.BusinessException;
import com.baseline.redis.util.RedisUtil;
import com.baseline.system.dto.SysDictTypeSaveDTO;
import com.baseline.system.entity.SysDictType;
import com.baseline.system.mapper.SysDictTypeMapper;
import com.baseline.system.service.ISysDictTypeService;
import com.baseline.system.vo.SysDictGroupVO;
import com.baseline.system.entity.SysDictData;
import com.baseline.system.service.ISysDictDataService;
import com.baseline.system.dto.SysDictGroupDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baseline.common.constant.CommonConstants;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Slf4j
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Resource
    private ISysDictDataService sysDictDataService;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public boolean saveOrUpdate(SysDictType entity) {
        if ( lambdaQuery()
                .ne(entity.getId()!=null,SysDictType::getId,entity.getId())
                .eq(SysDictType::getCode, entity.getCode()).exists()) {
            throw new BusinessException("存在相同code");
        }
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean saveOrUpdateWithCache(SysDictTypeSaveDTO dto) {
        SysDictType sysDictType = new SysDictType();
        BeanUtils.copyProperties(dto, sysDictType);
        boolean result = saveOrUpdate(sysDictType);
        
        // 清除对应的字典数据缓存
        if (result && dto.getCode() != null) {
            String cacheKey = CommonConstants.DICT_DATA_CACHE_PREFIX + dto.getCode();
            redisUtil.del(cacheKey);
        }
        
        return result;
    }

    @Override
    public boolean removeBatchByIdsWithCache(List<Long> ids) {
        // 先获取要删除的字典类型，以便清除相关缓存
        List<SysDictType> dictTypeList = listByIds(ids);
        
        boolean result = removeBatchByIds(ids);
        
        // 删除成功后清除相关字典数据缓存
        if (result && dictTypeList != null) {
            for (SysDictType dictType : dictTypeList) {
                if (dictType.getCode() != null) {
                    String cacheKey = CommonConstants.DICT_DATA_CACHE_PREFIX + dictType.getCode();
                    redisUtil.del(cacheKey);
                }
            }
        }
        
        return result;
    }


    @Override
    public List<SysDictGroupVO> getDictGroups(SysDictGroupDTO dto) {
        log.info("==========进入 getDictGroups");
        // dto 为 null 时初始化（避免 dto.getCode() 空指针）
        if (dto == null) {
            dto = new SysDictGroupDTO();
        }

        // 构建查询条件
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(dto.getCode()), SysDictType::getCode, dto.getCode())
                .eq(SysDictType::getStatus, CommonConstants.SYS_ENABLE)
                .eq(SysDictType::isDeleted, CommonConstants.SYS_DELETE)
                .orderByAsc(SysDictType::getId);
        log.info("查询条件: {}",JSONUtil.toJsonStr(queryWrapper));
        // 确保 dictTypes 非 null（MyBatis-Plus 通常返回空列表，但防御性编程）
        List<SysDictType> dictTypes = list(queryWrapper);
        if (dictTypes == null) {
            dictTypes = Collections.emptyList();
        }

        // 过滤源列表中的 null 元素（数据库异常时可能出现）
        dictTypes = dictTypes.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        log.info("dictTypes: {}",JSONUtil.toJsonStr(dictTypes));
        // BeanUtil.copyToList 在源列表含 null 时可能生成 null 元素
        List<SysDictGroupVO> result = BeanUtil.copyToList(dictTypes, SysDictGroupVO.class);
        if (result == null) {
            return Collections.emptyList(); // 直接返回空列表，避免后续操作
        }
        log.info("result before filter: {}",JSONUtil.toJsonStr(result));
        // 过滤 result 中的 null 元素（与 dictTypes 保持同步）
        result = result.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        log.info("result after filter: {}",JSONUtil.toJsonStr(result));
        // 使用增强 for 循环 + 显式判空（避免索引错位风险）
        for (int i = 0; i < result.size(); i++) {
            // 此时 dictTypes 与 result 长度严格一致（同源过滤），无需额外判空
            SysDictType dictType = dictTypes.get(i);
            log.info("dictType{}:{}",i,JSONUtil.toJsonStr(dictType));
            SysDictGroupVO groupVO = result.get(i);
            log.info("groupVO{}:{}",i,JSONUtil.toJsonStr(groupVO));

            // 字典数据查询结果安全处理（MyBatis-Plus 通常返回空列表，但加固）
            List<SysDictData> dictDataList = sysDictDataService.lambdaQuery()
                    .eq(SysDictData::getCode, dictType.getCode())
                    .eq(SysDictData::getStatus, CommonConstants.SYS_ENABLE)
                    .eq(SysDictData::isDeleted, CommonConstants.SYS_DELETE)
                    .orderByAsc(SysDictData::getSortNo)
                    .list();
            log.info("dictDataList: {}",JSONUtil.toJsonStr(dictDataList));
            // BeanUtil.copyToList 对空列表返回空列表，但加固 null 处理
            List<SysDictGroupVO.SysDictDataVO> dataVOList =
                    (dictDataList != null && !dictDataList.isEmpty())
                            ? BeanUtil.copyToList(dictDataList, SysDictGroupVO.SysDictDataVO.class)
                            : Collections.emptyList();
            log.info("dataVOList: {}",JSONUtil.toJsonStr(dictDataList));
            groupVO.setDictDataList(dataVOList); // groupVO 已过滤，安全
        }
        log.info("==========离开 getDictGroups");
        return result;
    }
        

}