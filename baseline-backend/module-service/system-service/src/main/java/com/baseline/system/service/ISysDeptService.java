package com.baseline.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baseline.system.dto.SysDeptFilterDTO;
import com.baseline.system.dto.SysDeptSaveDTO;
import com.baseline.system.entity.SysDept;
import com.baseline.system.vo.SysDeptNodeVO;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
public interface ISysDeptService extends IService<SysDept> {

    boolean saveOrUpdate(SysDeptSaveDTO dto);

    /**
     * 安全删除部门（检查是否有子节点）
     *
     * @param ids 部门ID列表
     * @return 删除结果
     */
    boolean safeRemoveByIds(List<Long> ids);

    List<SysDeptNodeVO> getNodeList(SysDeptFilterDTO dto);

    /**
     * 获取部门平铺列表
     *
     * @param dto 查询条件
     * @return 部门平铺列表
     */
    List<com.baseline.common.vo.SysDeptVO> getDeptList(SysDeptFilterDTO dto);
}
