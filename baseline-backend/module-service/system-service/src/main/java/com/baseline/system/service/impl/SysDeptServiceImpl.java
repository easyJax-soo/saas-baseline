package com.baseline.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baseline.utils.security.SecurityUtils;
import com.baseline.core.exception.BusinessException;
import com.baseline.system.dto.SysDeptFilterDTO;
import com.baseline.system.dto.SysDeptSaveDTO;
import com.baseline.system.entity.SysDept;
import com.baseline.system.mapper.SysDeptMapper;
import com.baseline.system.service.ISysDeptService;
import com.baseline.system.vo.SysDeptNodeVO;
import com.baseline.utils.tree.TreeUtils;
import com.baseline.system.mapper.SysUserMapper;
import com.baseline.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author csy
 * @since 2022-12-31
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public boolean saveOrUpdate(SysDeptSaveDTO dto) {
        SysDept entity = BeanUtil.copyProperties(dto, SysDept.class);
        
        // 检查是否是更新操作且父ID发生了变化
        boolean isUpdate = entity.getId() != null;
        Long oldParentId = null;
        if (isUpdate) {
            SysDept oldEntity = baseMapper.selectById(entity.getId());
            oldParentId = oldEntity != null ? oldEntity.getParentId() : null;
        }
        
        // 计算层级和父路径
        if (entity.getParentId() == null || entity.getParentId() == 0) {
            entity.setLevel(1);
            entity.setParentPath("0"); // 顶级节点的parentPath设置为"0"
        } else {
            // 防止循环引用：不能将自己设置为父节点
            if (entity.getId() != null && entity.getId().equals(entity.getParentId())) {
                throw new BusinessException("不能将自己设置为父部门");
            }
            
            SysDept parentEntity = baseMapper.selectById(entity.getParentId());
            if (ObjectUtil.isNull(parentEntity)) {
                throw new BusinessException("上级部门数据未找到");
            }
            
            // 防止循环引用：检查父节点的父路径中是否包含当前节点ID
            if (entity.getId() != null && StrUtil.isNotBlank(parentEntity.getParentPath())) {
                String[] parentPaths = parentEntity.getParentPath().split(",");
                for (String pathId : parentPaths) {
                    if (entity.getId().toString().equals(pathId)) {
                        throw new BusinessException("不能将子部门设置为父部门，会造成循环引用");
                    }
                }
            }
            
            // 设置层级为父级层级+1
            entity.setLevel(parentEntity.getLevel() + 1);
            
            // 构建父路径：父节点的parentPath + 父节点ID
            String parentPath = parentEntity.getParentPath();
            if (StrUtil.isNotBlank(parentPath)) {
                parentPath += ",";
            }
            parentPath += parentEntity.getId();
            entity.setParentPath(parentPath);
        }

        boolean result = saveOrUpdate(entity);
        
        // 只有在更新操作且父ID发生变化时，才需要更新子节点的层级和父路径
        if (result && isUpdate && !Objects.equals(oldParentId, entity.getParentId())) {
            updateChildrenLevelAndPath(entity);
        }
        
        return result;
    }
    
    /**
     * 递归更新子节点的层级和父路径
     */
    private void updateChildrenLevelAndPath(SysDept parentDept) {
        List<SysDept> children = lambdaQuery()
                .eq(SysDept::getParentId, parentDept.getId())
                .list();
        
        if (CollectionUtil.isEmpty(children)) {
            return;
        }
        
        for (SysDept child : children) {
            // 更新子节点的层级
            child.setLevel(parentDept.getLevel() + 1);
            
            // 更新子节点的父路径：父节点的parentPath + 父节点ID
            String newParentPath = parentDept.getParentPath();
            if (StrUtil.isNotBlank(newParentPath)) {
                newParentPath += ",";
            }
            newParentPath += parentDept.getId();
            child.setParentPath(newParentPath);
            
            // 更新数据库
            updateById(child);
            
            // 递归更新子节点的子节点
            updateChildrenLevelAndPath(child);
        }
    }

    @Override
    public boolean safeRemoveByIds(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return true;
        }
        
        // 检查每个要删除的部门是否有子节点
        for (Long id : ids) {
            List<SysDept> children = lambdaQuery()
                    .eq(SysDept::getParentId, id)
                    .list();
            
            if (CollectionUtil.isNotEmpty(children)) {
                SysDept dept = baseMapper.selectById(id);
                String deptName = dept != null ? dept.getName() : "ID:" + id;
                throw new BusinessException("部门【" + deptName + "】存在子部门，不能删除");
            }
        }
        
        // 如果所有部门都没有子节点，则可以安全删除
        return removeByIds(ids);
    }


    @Override
    public List<SysDeptNodeVO> getNodeList(SysDeptFilterDTO dto) {

        List<SysDept> sysDeptList = baseMapper.getNodeList(dto, SecurityUtils.getTenantId());

        if (sysDeptList.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 构建树形结构
        List<SysDeptNodeVO> nodeList = TreeUtils.buildTreeWithBeanCopy(sysDeptList, SysDeptNodeVO.class, null);
        
        // 填充负责人姓名
        fillLeaderUserName(nodeList);
        
        return nodeList;
    }
    
    /**
     * 递归填充负责人姓名
     */
    private void fillLeaderUserName(List<SysDeptNodeVO> nodeList) {
        if (CollectionUtils.isEmpty(nodeList)) {
            return;
        }
        
        // 收集所有负责人用户ID
        Set<Long> leaderUserIds = new HashSet<>();
        collectLeaderUserIds(nodeList, leaderUserIds);
        
        if (!leaderUserIds.isEmpty()) {
            // 批量查询用户信息
            List<SysUser> users = sysUserMapper.selectBatchIds(leaderUserIds);
            Map<Long, String> userIdToNameMap = users.stream()
                    .collect(Collectors.toMap(SysUser::getId, SysUser::getName));
            
            // 填充负责人姓名
            fillLeaderUserNameRecursive(nodeList, userIdToNameMap);
        }
    }
    
    /**
     * 递归收集所有负责人用户ID
     */
    private void collectLeaderUserIds(List<SysDeptNodeVO> nodeList, Set<Long> leaderUserIds) {
        for (SysDeptNodeVO node : nodeList) {
            if (node.getLeaderUserId() != null) {
                leaderUserIds.add(node.getLeaderUserId());
            }
            if (!CollectionUtils.isEmpty(node.getChildren())) {
                collectLeaderUserIds(node.getChildren(), leaderUserIds);
            }
        }
    }
    
    /**
     * 递归填充负责人姓名
     */
    private void fillLeaderUserNameRecursive(List<SysDeptNodeVO> nodeList, Map<Long, String> userIdToNameMap) {
        for (SysDeptNodeVO node : nodeList) {
            if (node.getLeaderUserId() != null) {
                node.setLeaderUserName(userIdToNameMap.get(node.getLeaderUserId()));
            }
            if (!CollectionUtils.isEmpty(node.getChildren())) {
                fillLeaderUserNameRecursive(node.getChildren(), userIdToNameMap);
            }
        }
    }

    @Override
    public List<com.baseline.common.vo.SysDeptVO> getDeptList(SysDeptFilterDTO dto) {
        // 使用现有的 getNodeList 查询，但不构建树结构
        List<SysDept> deptList = baseMapper.getNodeList(dto, SecurityUtils.getTenantId());
        
        // 转换为平铺的 SysDeptVO 列表
        return deptList.stream().map(dept -> {
            com.baseline.common.vo.SysDeptVO deptVO = new com.baseline.common.vo.SysDeptVO();
            deptVO.setId(dept.getId());
            deptVO.setName(dept.getName());
            deptVO.setCode(dept.getCode());
            deptVO.setParentId(dept.getParentId());
            deptVO.setCreateTime(dept.getCreateTime());
            return deptVO;
        }).collect(Collectors.toList());
    }

}
