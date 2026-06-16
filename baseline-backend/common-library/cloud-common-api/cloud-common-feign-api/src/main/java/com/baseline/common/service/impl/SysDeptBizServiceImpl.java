package com.baseline.common.service.impl;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.SysDeptFilterBizDTO;
import com.baseline.common.service.ISysDeptBizService;
import com.baseline.common.service.factory.RemoteDeptFallbackFactory;
import com.baseline.common.vo.SysDeptVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 系统部门服务业务接口 Feign 客户端实现
 *
 * @author cascade
 * @date 2025/11/16
 */
@FeignClient(contextId = "sysDeptBizService", value = "system-service", fallbackFactory = RemoteDeptFallbackFactory.class)
public interface SysDeptBizServiceImpl extends ISysDeptBizService {

    @Override
    @PostMapping("/feignApi/dept/deptList")
    List<SysDeptVO> getDeptVOList(@RequestBody SysDeptFilterBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
