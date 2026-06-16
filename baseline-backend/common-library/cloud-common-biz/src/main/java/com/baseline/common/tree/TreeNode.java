package com.baseline.common.tree;

import java.util.List;

/**
 * 树节点接口
 * 
 * @author system
 */
public interface TreeNode<T> {
    
    /**
     * 获取节点ID
     * @return 节点ID
     */
    Long getId();
    
    /**
     * 获取父节点ID
     * @return 父节点ID
     */
    Long getParentId();
    
    /**
     * 获取排序号
     * @return 排序号
     */
    Integer getSortNo();
    
    /**
     * 获取层级
     * @return 层级
     */
    Integer getLevel();
    
    /**
     * 获取子节点列表
     * @return 子节点列表
     */
    List<T> getChildren();
    
    /**
     * 设置子节点列表
     * @param children 子节点列表
     */
    void setChildren(List<T> children);
}
