package com.baseline.common.service.impl;

import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.HandlePointsDTO;
import com.baseline.common.dto.PointOrganizeUserBindBIzDTO;
import com.baseline.common.dto.SysUserFilterBizDTO;
import com.baseline.common.service.IPointsBizService;
import com.baseline.common.service.factory.RemotePointsFallbackFactory;
import com.baseline.common.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 积分服务业务接口 Feign 客户端实现
 *
 * @author cascade
 * @date 2025/11/15
 */
@FeignClient(contextId = "pointsBizService", value = "points-service", fallbackFactory = RemotePointsFallbackFactory.class)
public interface PointsBizServiceImpl extends IPointsBizService {

    @Override
    @GetMapping("/feignApi/user/detail/{id}")
    SysUserSaveVO getUserDetail(@PathVariable("id") Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @PostMapping("/feignApi/user/simpleList")
    List<SysUserVO> getSimpleList(@RequestBody SysUserFilterBizDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @PostMapping("/feignApi/point/organizeBind")
    boolean organizeBind(@RequestBody PointOrganizeUserBindBIzDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @GetMapping("/feignApi/point/organizeInfo")
    PointVillageOrganizationBizVO organizeInfo(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @PostMapping("/feignApi/user/deductPoints")
    Boolean deductPoints(@RequestBody HandlePointsDTO dto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @GetMapping("/feignApi/user/pointsTop")
    List<SystemUserPointsTopVO> pointsTop(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @Override
    @GetMapping("/feignApi/user/userPoints")
    SystemUserPointsVO userPoints(@RequestParam("id") Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
