package com.baseline.common.service;

import com.baseline.common.dto.SysDeptFilterBizDTO;
import com.baseline.common.vo.SysDeptVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统部门服务业务接口
 *
 * @author cascade
 * @date 2025/11/16
 */
@Service
public interface ISysDeptBizService {

    /**
     * 获取部门列表
     *
     * @param dto 查询条件
     * @param source 来源标识
     * @return 部门列表
     */
    List<SysDeptVO> getDeptVOList(SysDeptFilterBizDTO dto, String source);
}
