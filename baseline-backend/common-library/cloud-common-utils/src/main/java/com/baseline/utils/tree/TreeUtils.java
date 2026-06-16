package com.baseline.utils.tree;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baseline.common.tree.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 树结构工具类
 * 
 * @author system
 */
public class TreeUtils {
    
    /**
     * 构建树结构 - 优化算法，时间复杂度 O(n)
     * 
     * @param nodeList 节点列表
     * @param filterIds 过滤的节点ID列表，如果为null则不过滤
     * @param <T> 节点类型
     * @return 树结构列表
     */
    public static <T extends TreeNode<T>> List<T> buildTree(List<T> nodeList, List<Long> filterIds) {
        if (CollectionUtil.isEmpty(nodeList)) {
            return new ArrayList<>();
        }
        
        // 使用Map提升查找效率，时间复杂度从O(n²)降到O(n)
        Map<Long, T> nodeMap = new HashMap<>();
        List<T> rootNodes = new ArrayList<>();
        
        // 1. 初始化节点映射
        for (T node : nodeList) {
            if (node.getChildren() == null) {
                node.setChildren(new ArrayList<>());
            }
            nodeMap.put(node.getId(), node);
        }

        // 2. 构建父子关系
        for (T node : nodeList) {
            Long parentId = node.getParentId();
            
            if (parentId == null || parentId == 0) {
                // 根节点
                rootNodes.add(node);
            } else {
                // 子节点，添加到父节点的children中
                T parent = nodeMap.get(parentId);
                if (parent != null) {
                    parent.getChildren().add(node);
                } else {
                    // 父节点不存在，当作根节点处理
                    rootNodes.add(node);
                }
            }
        }

        // 3. 应用过滤条件
        if (CollectionUtil.isNotEmpty(filterIds)) {
            Set<Long> filterSet = new HashSet<>(filterIds);
            rootNodes = filterNodes(rootNodes, filterSet);
        }
        
        // 4. 排序
        sortTree(rootNodes);

        return rootNodes;
    }
    
    /**
     * 过滤节点（保留指定ID的节点及其祖先节点）
     * 
     * @param nodes 节点列表
     * @param filterIds 过滤ID集合
     * @param <T> 节点类型
     * @return 过滤后的节点列表
     */
    private static <T extends TreeNode<T>> List<T> filterNodes(List<T> nodes, Set<Long> filterIds) {
        List<T> result = new ArrayList<>();
        
        for (T node : nodes) {
            if (shouldKeepNodeOptimized(node, filterIds)) {
                // 递归过滤子节点
                if (CollectionUtil.isNotEmpty(node.getChildren())) {
                    List<T> filteredChildren = filterNodes(node.getChildren(), filterIds);
                    node.setChildren(filteredChildren);
                }
                result.add(node);
            }
        }
        
        return result;
    }
    
    /**
     * 优化的节点保留判断
     * 
     * @param node 节点
     * @param filterIds 过滤ID集合
     * @param <T> 节点类型
     * @return 是否保留
     */
    private static <T extends TreeNode<T>> boolean shouldKeepNodeOptimized(T node, Set<Long> filterIds) {
        // 如果当前节点在过滤列表中，保留
        if (filterIds.contains(node.getId())) {
            return true;
        }
        
        // 如果子节点中有需要保留的，也保留当前节点
        if (CollectionUtil.isNotEmpty(node.getChildren())) {
            for (T child : node.getChildren()) {
                if (shouldKeepNodeOptimized(child, filterIds)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * 对树结构进行排序
     * 
     * @param nodeList 节点列表
     * @param <T> 节点类型
     */
    private static <T extends TreeNode<T>> void sortTree(List<T> nodeList) {
        nodeList.sort(Comparator.<T>comparingInt(TreeNode::getSortNo));
        for (T node : nodeList) {
            if (CollectionUtil.isNotEmpty(node.getChildren())) {
                sortTree(node.getChildren());
            }
        }
    }
    
    /**
     * 从原始数据构建树结构（适用于实体类转换为VO的场景）
     * 
     * @param sourceList 原始数据列表
     * @param converter 转换器函数
     * @param filterIds 过滤的节点ID列表
     * @param <S> 原始数据类型
     * @param <T> 目标节点类型
     * @return 树结构列表
     */
    public static <S, T extends TreeNode<T>> List<T> buildTreeFromSource(
            List<S> sourceList, 
            TreeNodeConverter<S, T> converter, 
            List<Long> filterIds) {
        
        if (CollectionUtil.isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        
        // 对象转换
        List<T> nodeList = sourceList.stream()
                .map(converter::convert)
                .collect(Collectors.toList());

        return buildTree(nodeList, filterIds);
    }
    
    /**
     * 使用BeanUtil进行属性复制的便捷方法
     * 
     * @param sourceList 原始数据列表
     * @param targetClass 目标类型
     * @param filterIds 过滤的节点ID列表
     * @param <S> 原始数据类型
     * @param <T> 目标节点类型
     * @return 树结构列表
     */
    public static <S, T extends TreeNode<T>> List<T> buildTreeWithBeanCopy(
            List<S> sourceList, 
            Class<T> targetClass, 
            List<Long> filterIds) {
        
        List<T> result = buildTreeFromSource(sourceList, source -> {
            T node = BeanUtil.copyProperties(source, targetClass);
            node.setChildren(new ArrayList<>());
            return node;
        }, filterIds);
        
        return result;
    }
    
    /**
     * 树节点转换器接口
     * 
     * @param <S> 源类型
     * @param <T> 目标类型
     */
    @FunctionalInterface
    public interface TreeNodeConverter<S, T> {
        T convert(S source);
    }
}
