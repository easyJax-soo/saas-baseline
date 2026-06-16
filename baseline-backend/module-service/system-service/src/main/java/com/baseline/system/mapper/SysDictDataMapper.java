package com.baseline.system.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baseline.common.dto.DictBizDTO;
import com.baseline.common.vo.DictBizVO;
import com.baseline.system.entity.SysDictData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典数据表 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    @InterceptorIgnore(tenantLine = "true")
    List<DictBizVO> getDictDataByDictCode(@Param("dto") DictBizDTO dto);
}
