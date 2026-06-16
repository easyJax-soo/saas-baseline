package com.baseline.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.system.dto.*;
import com.baseline.system.entity.SysTenant;
import com.baseline.system.mapper.SysTenantMapper;
import com.baseline.system.service.ISysTenantService;
import com.baseline.system.service.ISysTenantMenuService;
import com.baseline.system.service.ISysTenantPermissionService;
import com.baseline.system.service.ISysTenantProjectService;
import com.baseline.system.vo.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 租户信息表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Slf4j
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements ISysTenantService {

    @Resource
    private ISysTenantMenuService sysTenantMenuService;

    @Resource
    private ISysTenantPermissionService sysTenantPermissionService;

    @Resource
    private ISysTenantProjectService sysTenantProjectService;


    @Override
    public boolean saveOrUpdate(SysTenantSaveDTO dto) {
        // 验证父租户设置
        if (dto.getParentId() != null && dto.getParentId() > 0) {
            if (dto.getId() != null && !canSetParent(dto.getId(), dto.getParentId())) {
                throw new RuntimeException("不能设置该父租户，会形成循环引用");
            }
        }
        
        // 编辑时检查父租户是否发生变化
        Long oldParentId = null;
        if (dto.getId() != null) {
            SysTenant oldTenant = getById(dto.getId());
            if (oldTenant != null) {
                oldParentId = oldTenant.getParentId();
            }
        }
        
        SysTenant entity = BeanUtil.copyProperties(dto, SysTenant.class);
        
        // 更新层级信息
        updateTenantHierarchy(entity);

        int res;
        if (dto.getId() != null){
            res = baseMapper.updateById(entity);
            // 编辑后检查是否需要更新子租户层级
            if (res > 0 && !Objects.equals(oldParentId, dto.getParentId())) {
                updateChildrenHierarchy(entity.getId());
            }
        }else{
            res = baseMapper.insert(entity);
            // 新增后更新parentPath
            if (res > 0) {
                updateParentPathAfterInsert(entity);
            }
        }
        return res > 0;
    }

    /**
     * 新增租户后更新parentPath
     */
    private void updateParentPathAfterInsert(SysTenant tenant) {
        if (tenant.getId() != null) {
            String newParentPath;
            if (tenant.getParentId() == null || tenant.getParentId() == 0) {
                newParentPath = String.valueOf(tenant.getId());
            } else {
                SysTenant parent = getById(tenant.getParentId());
                if (parent != null) {
                    newParentPath = parent.getParentPath() + "," + tenant.getId();
                } else {
                    newParentPath = String.valueOf(tenant.getId());
                }
            }
            
            // 更新parentPath
            SysTenant updateEntity = new SysTenant();
            updateEntity.setId(tenant.getId());
            updateEntity.setParentPath(newParentPath);
            baseMapper.updateById(updateEntity);
        }
    }

    @Override
    public SysTenantVO detail(SysTenantDetailDTO dto) {
        SysTenant entity = baseMapper.selectById(dto.getId());
        return BeanUtil.copyProperties(entity, SysTenantVO.class);
    }

    @Override
    public boolean remove(List<Long> ids) {
        int res = baseMapper.deleteBatchIds(ids);
        return res > 0;
    }

    @Override
    public IPage<SysTenantVO> pageTenant(SysTenantFilterDTO dto) {
        Page<SysTenantVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return baseMapper.pageTenant(page, dto);
    }

    @Override
    public SysTenantResourceVO getTenantResource(SysTenantResourceDTO dto) {
        SysTenantResourceVO vo = new SysTenantResourceVO();
        vo.setTenantId(dto.getTenantId());
        
        // 获取租户名称
        SysTenantDetailDTO tenantDetailDTO = new SysTenantDetailDTO();
        tenantDetailDTO.setId(dto.getTenantId());
        SysTenantVO tenantInfo = detail(tenantDetailDTO);
        if (tenantInfo != null) {
            vo.setTenantName(tenantInfo.getName());
        }
        
        // 获取租户权限ID列表
        List<Long> permissionIds = sysTenantPermissionService.getDetailByTenantId(dto.getTenantId());
        vo.setPermissionIds(permissionIds != null ? permissionIds : Collections.emptyList());
        
        // 获取租户菜单ID列表
        List<Long> menuIds = sysTenantMenuService.getDetailByTenantId(dto.getTenantId());
        vo.setMenuIds(menuIds != null ? menuIds : Collections.emptyList());
        
        // 获取租户项目编码列表
        List<String> projectCodes = sysTenantProjectService.getProjectCodesByTenantId(dto.getTenantId());
        vo.setProjectCodes(projectCodes != null ? projectCodes : Collections.emptyList());
        
        return vo;
    }

    @Override
    public boolean saveTenantResource(SysTenantResourceSaveDTO dto) {
        boolean success = true;
        
        try {
            // 保存租户权限
            if (dto.getPermissionIds() != null) {
                SysTenantPermissionSaveDTO permissionSaveDTO = new SysTenantPermissionSaveDTO();
                permissionSaveDTO.setTenantId(dto.getTenantId());
                permissionSaveDTO.setPermissionIds(dto.getPermissionIds());
                success &= sysTenantPermissionService.saveOrUpdate(permissionSaveDTO);
            }
            
            // 保存租户菜单
            if (dto.getMenuIds() != null) {
                SysTenantMenuSaveDTO menuSaveDTO = new SysTenantMenuSaveDTO();
                menuSaveDTO.setTenantId(dto.getTenantId());
                menuSaveDTO.setMenuIds(dto.getMenuIds());
                success &= sysTenantMenuService.saveOrUpdate(menuSaveDTO);
            }
            
            // 保存租户项目
            if (dto.getProjectCodes() != null) {
                success &= sysTenantProjectService.saveTenantProjects(dto.getTenantId(), dto.getProjectCodes());
            }
            
        } catch (Exception e) {
            return false;
        }
        
        return success;
    }

    // ========== 层级相关方法实现 ==========

    @Override
    public List<SysTenantTreeVO> getTenantTree(Long parentId) {
        LambdaQueryWrapper<SysTenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysTenant::getParentId, parentId == null ? 0 : parentId)
               .eq(SysTenant::getDeleted, false)
               .orderByAsc(SysTenant::getSort, SysTenant::getId);
        
        List<SysTenant> tenants = list(wrapper);
        List<SysTenantTreeVO> result = new ArrayList<>();
        
        for (SysTenant tenant : tenants) {
            SysTenantTreeVO vo = BeanUtil.copyProperties(tenant, SysTenantTreeVO.class);
            
            // 设置父租户名称
            if (tenant.getParentId() != null && tenant.getParentId() > 0) {
                SysTenant parent = getById(tenant.getParentId());
                if (parent != null) {
                    vo.setParentName(parent.getName());
                }
            }
            
            // 递归获取子租户
            List<SysTenantTreeVO> children = getTenantTree(tenant.getId());
            vo.setChildren(children);
            vo.setHasChildren(!children.isEmpty());
            
            result.add(vo);
        }
        
        return result;
    }

    @Override
    public SysTenantDetailVO getTenantDetail(Long id) {
        SysTenant tenant = getById(id);
        if (tenant == null) {
            return null;
        }
        
        SysTenantDetailVO vo = BeanUtil.copyProperties(tenant, SysTenantDetailVO.class);
        
        // 设置父租户名称
        if (tenant.getParentId() != null && tenant.getParentId() > 0) {
            SysTenant parent = getById(tenant.getParentId());
            if (parent != null) {
                vo.setParentName(parent.getName());
            }
        }
        
        // 统计子租户数量
        LambdaQueryWrapper<SysTenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysTenant::getParentId, id)
               .eq(SysTenant::getDeleted, false);
        long childrenCount = count(wrapper);
        vo.setChildrenCount((int) childrenCount);
        
        return vo;
    }

    @Override
    public List<Long> getTenantAndChildrenIds(Long tenantId) {
        List<Long> result = new ArrayList<>();
        result.add(tenantId);
        
        LambdaQueryWrapper<SysTenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysTenant::getParentId, tenantId)
               .eq(SysTenant::getDeleted, false);
        
        List<SysTenant> children = list(wrapper);
        for (SysTenant child : children) {
            result.addAll(getTenantAndChildrenIds(child.getId()));
        }
        
        return result;
    }

    @Override
    public List<Long> getTenantAndParentIds(Long tenantId) {
        List<Long> result = new ArrayList<>();
        result.add(tenantId);
        
        SysTenant tenant = getById(tenantId);
        if (tenant != null && tenant.getParentId() != null && tenant.getParentId() > 0) {
            result.addAll(getTenantAndParentIds(tenant.getParentId()));
        }
        
        return result;
    }

    @Override
    public boolean canSetParent(Long tenantId, Long parentId) {
        if (parentId == null || parentId == 0) {
            return true; // 设置为顶级租户
        }
        
        if (tenantId.equals(parentId)) {
            return false; // 不能设置自己为父租户
        }
        
        // 检查是否会形成循环引用
        List<Long> childrenIds = getTenantAndChildrenIds(tenantId);
        return !childrenIds.contains(parentId);
    }


    /**
     * 递归更新子租户的层级信息
     */
    private void updateChildrenHierarchy(Long parentTenantId) {
        // 获取直接子租户
        LambdaQueryWrapper<SysTenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysTenant::getParentId, parentTenantId)
               .eq(SysTenant::getDeleted, false);
        
        List<SysTenant> children = list(wrapper);
        if (children.isEmpty()) {
            return;
        }
        
        // 获取父租户信息
        SysTenant parent = getById(parentTenantId);
        if (parent == null) {
            return;
        }
        
        // 更新每个子租户的层级信息
        for (SysTenant child : children) {
            // 重新计算层级和路径
            child.setLevel(parent.getLevel() + 1);
            child.setParentPath(parent.getParentPath() + "," + child.getId());
            
            // 更新数据库
            baseMapper.updateById(child);
            
            // 递归更新子租户的子租户
            updateChildrenHierarchy(child.getId());
        }
    }

    /**
     * 更新租户层级信息
     */
    private void updateTenantHierarchy(SysTenant tenant) {
        if (tenant.getParentId() == null || tenant.getParentId() == 0) {
            // 顶级租户
            tenant.setLevel(1);
            // 新增时ID为空，parentPath在保存后更新
            if (tenant.getId() != null) {
                tenant.setParentPath(String.valueOf(tenant.getId()));
            } else {
                tenant.setParentPath(""); // 临时设置，保存后会更新
            }
        } else {
            // 子租户
            SysTenant parent = getById(tenant.getParentId());
            if (parent != null) {
                tenant.setLevel(parent.getLevel() + 1);
                // 新增时ID为空，parentPath在保存后更新
                if (tenant.getId() != null) {
                    tenant.setParentPath(parent.getParentPath() + "," + tenant.getId());
                } else {
                    tenant.setParentPath(parent.getParentPath()); // 临时设置，保存后会更新
                }
            }
        }
        
        // 设置默认排序
        if (tenant.getSort() == null) {
            tenant.setSort(0);
        }
    }

    @Override
    public List<SysTenantVO> getTenantList(SysTenantFilterDTO dto) {
        LambdaQueryWrapper<SysTenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysTenant::getDeleted, false);
        
        // 添加查询条件
        if (dto != null) {
            if (dto.getId() != null) {
                wrapper.eq(SysTenant::getId, dto.getId());
            }
            if (StrUtil.isNotBlank(dto.getName())) {
                wrapper.like(SysTenant::getName, dto.getName());
            }
            if (StrUtil.isNotBlank(dto.getCode())) {
                wrapper.like(SysTenant::getCode, dto.getCode());
            }
            if (dto.getParentId() != null) {
                wrapper.eq(SysTenant::getParentId, dto.getParentId());
            }
            if (dto.getStatus() != null) {
                wrapper.eq(SysTenant::getStatus, dto.getStatus());
            }
        }
        
        wrapper.orderByAsc(SysTenant::getSort, SysTenant::getId);
        
        List<SysTenant> list = list(wrapper);
        return list.stream()
                .map(tenant -> {
                    SysTenantVO vo = BeanUtil.copyProperties(tenant, SysTenantVO.class);
                    
                    // 设置父租户名称
                    if (tenant.getParentId() != null && tenant.getParentId() > 0) {
                        SysTenant parent = getById(tenant.getParentId());
                        if (parent != null && !parent.getDeleted()) {
                            vo.setParentName(parent.getName());
                        }
                    }
                    
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<SysTenantTreeVO> getTenantTree(SysTenantFilterDTO dto) {
        // 获取所有租户数据
        List<SysTenantVO> allTenants = getTenantList(dto);
        log.info("getTenantTree - 获取到租户数据数量: {}", allTenants.size());
        
        // 打印所有租户的基本信息
        for (SysTenantVO tenant : allTenants) {
            log.info("租户信息: ID={}, Name={}, ParentId={}", tenant.getId(), tenant.getName(), tenant.getParentId());
        }
        
        if (allTenants.isEmpty()) {
            log.warn("getTenantTree - 没有找到租户数据，查询条件: {}", dto);
            return new ArrayList<>();
        }
        
        // 转换为树形结构
        List<SysTenantTreeVO> treeList = allTenants.stream()
                .map(tenant -> BeanUtil.copyProperties(tenant, SysTenantTreeVO.class))
                .collect(Collectors.toList());
        
        // 构建树形结构 - 先尝试查找parentId为null的根节点
        List<SysTenantTreeVO> result = buildTenantTree(treeList, null);
        log.info("getTenantTree - 构建树形结构完成(parentId=null)，根节点数量: {}", result.size());
        
        // 如果没有找到parentId=null的根节点，尝试查找parentId=0的根节点
        if (result.isEmpty() && !treeList.isEmpty()) {
            log.warn("没有找到根节点(parentId=null)，尝试查找parentId=0的根节点");
            result = buildTenantTree(treeList, 0L);
            log.info("getTenantTree - 构建树形结构完成(parentId=0)，根节点数量: {}", result.size());
        }
        
        // 如果还是没有找到根节点，尝试找到最顶层的节点（parentId不在当前列表中的节点）
        if (result.isEmpty() && !treeList.isEmpty()) {
            log.warn("没有找到根节点(parentId=null或parentId=0)，尝试查找最顶层节点");
            Set<Long> allIds = treeList.stream().map(SysTenantTreeVO::getId).collect(Collectors.toSet());
            
            for (SysTenantTreeVO tenant : treeList) {
                if (tenant.getParentId() != null && !allIds.contains(tenant.getParentId())) {
                    log.info("找到最顶层节点: {} (ID: {}, ParentId: {})", tenant.getName(), tenant.getId(), tenant.getParentId());
                    // 递归获取子节点
                    List<SysTenantTreeVO> children = buildTenantTree(treeList, tenant.getId());
                    tenant.setChildren(children);
                    tenant.setHasChildren(!children.isEmpty());
                    result.add(tenant);
                }
            }
            log.info("最顶层节点数量: {}", result.size());
        }
        
        return result;
    }

    /**
     * 构建租户树形结构
     */
    private List<SysTenantTreeVO> buildTenantTree(List<SysTenantTreeVO> allTenants, Long parentId) {
        List<SysTenantTreeVO> result = new ArrayList<>();
        log.debug("buildTenantTree - 构建树形结构，parentId: {}, 总租户数: {}", parentId, allTenants.size());
        
        for (SysTenantTreeVO tenant : allTenants) {
            if (Objects.equals(tenant.getParentId(), parentId)) {
                log.debug("buildTenantTree - 找到子节点: {} (ID: {}, ParentId: {})", tenant.getName(), tenant.getId(), tenant.getParentId());
                // 递归获取子节点
                List<SysTenantTreeVO> children = buildTenantTree(allTenants, tenant.getId());
                tenant.setChildren(children);
                tenant.setHasChildren(!children.isEmpty());
                result.add(tenant);
            }
        }
        
        // 按排序字段排序
        result.sort(Comparator.comparing(SysTenantTreeVO::getSort, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(SysTenantTreeVO::getId));
        
        return result;
    }

    @Override
    public SysTenantVO getTenantDetail(SysTenantDetailDTO dto) {
        if (dto == null) {
            return null;
        }

        LambdaQueryWrapper<SysTenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysTenant::getDeleted, false);
        
        // 支持通过ID或编码查询
        if (dto.getId() != null) {
            wrapper.eq(SysTenant::getId, dto.getId());
        } else if (StrUtil.isNotBlank(dto.getCode())) {
            wrapper.eq(SysTenant::getCode, dto.getCode());
        } else {
            return null; // 没有提供查询条件
        }
        
        SysTenant tenant = getOne(wrapper);
        if (tenant == null) {
            return null;
        }
        
        SysTenantVO tenantVO = BeanUtil.copyProperties(tenant, SysTenantVO.class);
        
        // 设置父租户名称
        if (tenant.getParentId() != null && tenant.getParentId() > 0) {
            SysTenant parentTenant = getById(tenant.getParentId());
            if (parentTenant != null && !parentTenant.getDeleted()) {
                tenantVO.setParentName(parentTenant.getName());
            }
        }
        
        return tenantVO;
    }
}
