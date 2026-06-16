package com.baseline.utils.options;

import cn.hutool.core.collection.CollectionUtil;
import com.baseline.common.vo.TreeOptionVO;
import com.baseline.common.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 下拉选项工具类
 * 
 * @author system
 */
public class OptionsUtils {

    /**
     * 选项转换器接口
     * 
     * @param <T> 源类型
     */
    @FunctionalInterface
    public interface OptionConverter<T> {
        TreeOptionVO convert(T source);
    }

    /**
     * 从树形节点列表转换为树形选项列表
     * 
     * @param nodeList 树形节点列表
     * @param valueMapper 值映射函数
     * @param labelMapper 标签映射函数
     * @param <T> 节点类型
     * @return 树形选项列表
     */
    public static <T extends TreeNode<T>> List<TreeOptionVO> fromTreeNodeList(
            List<T> nodeList, 
            Function<T, String> valueMapper, 
            Function<T, String> labelMapper) {
        
        if (CollectionUtil.isEmpty(nodeList)) {
            return new ArrayList<>();
        }
        
        return nodeList.stream()
                .map(node -> convertToTreeOption(node, valueMapper, labelMapper))
                .collect(Collectors.toList());
    }

    /**
     * 从实体列表转换为树形选项列表（平铺结构）
     * 
     * @param entityList 实体列表
     * @param valueMapper 值映射函数
     * @param labelMapper 标签映射函数
     * @param <T> 实体类型
     * @return 树形选项列表
     */
    public static <T> List<TreeOptionVO> fromEntityListToTreeOptions(
            List<T> entityList, 
            Function<T, String> valueMapper, 
            Function<T, String> labelMapper) {
        
        if (CollectionUtil.isEmpty(entityList)) {
            return new ArrayList<>();
        }
        
        return entityList.stream()
                .map(entity -> createFlatOption(valueMapper.apply(entity), labelMapper.apply(entity)))
                .collect(Collectors.toList());
    }

    /**
     * 创建平铺选项（无子节点）
     * 
     * @param value 选项值
     * @param label 选项标签
     * @return 树形选项
     */
    private static TreeOptionVO createFlatOption(String value, String label) {
        TreeOptionVO option = new TreeOptionVO();
        option.setValue(value);
        option.setLabel(label);
        option.setChildren(new ArrayList<>());
        return option;
    }

    /**
     * 递归转换树形节点为树形选项
     * 
     * @param node 树形节点
     * @param valueMapper 值映射函数
     * @param labelMapper 标签映射函数
     * @param <T> 节点类型
     * @return 树形选项
     */
    private static <T extends TreeNode<T>> TreeOptionVO convertToTreeOption(
            T node, 
            Function<T, String> valueMapper, 
            Function<T, String> labelMapper) {
        
        TreeOptionVO option = new TreeOptionVO();
        option.setValue(valueMapper.apply(node));
        option.setLabel(labelMapper.apply(node));
        
        // 递归转换子节点
        List<TreeOptionVO> children = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(node.getChildren())) {
            children = node.getChildren().stream()
                    .map(child -> convertToTreeOption(child, valueMapper, labelMapper))
                    .collect(Collectors.toList());
        }
        option.setChildren(children);
        
        return option;
    }
}
