package com.baseline.system.controller.feign;

import cn.hutool.core.bean.BeanUtil;
import com.baseline.common.constant.SecurityConstants;
import com.baseline.common.dto.SysTenantDetailBizDTO;
import com.baseline.common.dto.SysTenantFilterBizDTO;
import com.baseline.common.dto.TenantHierarchyBizDTO;
import com.baseline.common.vo.SysTenantBizVO;
import com.baseline.common.vo.SysTenantTreeBizVO;
import com.baseline.system.dto.SysTenantDetailDTO;
import com.baseline.system.dto.SysTenantFilterDTO;
import com.baseline.system.service.ISysTenantService;
import com.baseline.system.vo.SysTenantTreeVO;
import com.baseline.system.vo.SysTenantVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * [Feign]-租户服务接口
 *
 * @author cascade
 * @date 2025/11/17
 */
@Api(tags = "[Feign]-租户接口")
@RestController
@RequestMapping("/tenant")
public class SysTenantFeignController {

    @Resource
    private ISysTenantService sysTenantService;

    /**
     * 获取租户的所有子租户ID（包括自己）
     * 用于层级租户数据权限控制
     *
     * @param dto 租户层级查询条件
     * @param source 来源标识
     * @return 租户及其所有子租户ID列表
     */
    @ApiOperation("[Feign]-获取租户层级ID列表")
    @PostMapping("/children")
    public List<Long> getTenantAndChildrenIds(@RequestBody TenantHierarchyBizDTO dto,
                                              @RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        return sysTenantService.getTenantAndChildrenIds(dto.getTenantId());
    }

    /**
     * 获取租户列表
     *
     * @param dto 查询条件
     * @param source 来源标识
     * @return 租户列表
     */
    @ApiOperation("[Feign]-获取租户列表")
    @PostMapping("/list")
    public List<SysTenantBizVO> getTenantList(@RequestBody SysTenantFilterBizDTO dto,
                                              @RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        // 转换DTO
        SysTenantFilterDTO filterDTO = BeanUtil.copyProperties(dto, SysTenantFilterDTO.class);
        
        // 调用服务获取数据
        List<SysTenantVO> tenantList = sysTenantService.getTenantList(filterDTO);
        
        // 转换为业务VO
        return tenantList.stream()
                .map(tenant -> BeanUtil.copyProperties(tenant, SysTenantBizVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 获取租户树形结构
     *
     * @param dto 查询条件
     * @param source 来源标识
     * @return 租户树形结构列表
     */
    @ApiOperation("[Feign]-获取租户树形结构")
    @PostMapping("/tree")
    public List<SysTenantTreeBizVO> getTenantTree(@RequestBody SysTenantFilterBizDTO dto,
                                                  @RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        // 转换DTO
        SysTenantFilterDTO filterDTO = BeanUtil.copyProperties(dto, SysTenantFilterDTO.class);
        
        // 调用服务获取数据
        List<SysTenantTreeVO> tenantTree = sysTenantService.getTenantTree(filterDTO);
        
        // 转换为业务VO
        return convertToTreeBizVO(tenantTree);
    }

    /**
     * 获取租户详情
     *
     * @param dto 查询条件
     * @param source 来源标识
     * @return 租户详情
     */
    @ApiOperation("[Feign]-获取租户详情")
    @PostMapping("/detail")
    public SysTenantBizVO getTenantDetail(@RequestBody SysTenantDetailBizDTO dto,
                                          @RequestHeader(SecurityConstants.FROM_SOURCE) String source) {
        // 转换DTO
        SysTenantDetailDTO detailDTO = BeanUtil.copyProperties(dto, SysTenantDetailDTO.class);
        
        // 调用服务获取数据
        SysTenantVO tenantVO = sysTenantService.getTenantDetail(detailDTO);
        
        // 转换为业务VO
        return tenantVO != null ? BeanUtil.copyProperties(tenantVO, SysTenantBizVO.class) : null;
    }

    /**
     * 递归转换租户树形结构
     */
    private List<SysTenantTreeBizVO> convertToTreeBizVO(List<SysTenantTreeVO> treeList) {
        return treeList.stream().map(tree -> {
            SysTenantTreeBizVO bizVO = BeanUtil.copyProperties(tree, SysTenantTreeBizVO.class);
            if (tree.getChildren() != null && !tree.getChildren().isEmpty()) {
                bizVO.setChildren(convertToTreeBizVO(tree.getChildren()));
            }
            return bizVO;
        }).collect(Collectors.toList());
    }
}
