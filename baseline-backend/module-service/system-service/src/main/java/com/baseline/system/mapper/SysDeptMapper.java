package com.baseline.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baseline.mybatis.annotation.DataColumn;
import com.baseline.mybatis.annotation.DataPermission;
import com.baseline.system.dto.SysDeptFilterDTO;
import com.baseline.system.entity.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {


    @DataPermission({
            @DataColumn(key = "deptName", value = "id"),
    })
    List<SysDept> getNodeList(@Param("dto") SysDeptFilterDTO dto, @Param("tenantId") Long tenantId);

}
