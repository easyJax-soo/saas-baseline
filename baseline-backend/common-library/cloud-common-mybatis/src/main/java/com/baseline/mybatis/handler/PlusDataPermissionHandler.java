package com.baseline.mybatis.handler;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
//import com.baseline.common.server.RemoteUserService;
//import com.baseline.common.vo.SysRoleVO;
//import com.baseline.common.vo.SysUserLoginVO;
import com.baseline.common.vo.LoginUserBizVO;
import com.baseline.core.exception.BusinessException;
import com.baseline.core.utils.StringUtils;
import com.baseline.mybatis.annotation.DataColumn;
import com.baseline.mybatis.annotation.DataPermission;
import com.baseline.mybatis.utils.SpringUtils;
import com.baseline.utils.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 数据权限过滤
 *
 * @author Lion Li
 * @version 3.5.0
 */
@Slf4j
public class PlusDataPermissionHandler {

//    public RemoteUserService remoteUserService = SpringUtil.getBean(RemoteUserService.class);

//    TODO
//    @Lazy
//    @Resource
//    private RemoteUserService remoteUserService;

    /**
     * 方法或类(名称) 与 注解的映射关系缓存
     */
    private final Map<String, DataPermission> dataPermissionCacheMap = new ConcurrentHashMap<>();

    /**
     * 无效注解方法缓存用于快速返回
     */
    private final Set<String> invalidCacheSet = new ConcurrentHashSet<>();

    /**
     * spel 解析器
     */
    private final ExpressionParser parser = new SpelExpressionParser();
    private final ParserContext parserContext = new TemplateParserContext();
    /**
     * bean解析器 用于处理 spel 表达式中对 bean 的调用
     */
    private final BeanResolver beanResolver = new BeanFactoryResolver(SpringUtils.getBeanFactory());

    public Expression getSqlSegment(Expression where, String mappedStatementId, boolean isSelect) {
        DataColumn[] dataColumns = findAnnotation(mappedStatementId);
        if (ArrayUtil.isEmpty(dataColumns)) {
            invalidCacheSet.add(mappedStatementId);
            return where;
        }

        LoginUserBizVO loginUser = SecurityUtils.getLoginUser();
//        TODO
//        SysUserLoginVO currentUser = null;
//        if(ObjectUtil.isNotNull(loginUser)){
////            currentUser = loginUser.getSysUserLoginVO();
//            DataPermissionHelper.setVariable("user", currentUser);
//        }
//
//        // 如果是超级管理员，则不过滤数据
//        if (ObjectUtil.isNull(currentUser) || SecurityUtils.isAdmin(currentUser.getId())) {
//            return where;
//        }
        String dataFilterSql = buildDataFilter(dataColumns, isSelect);
        if (StringUtils.isBlank(dataFilterSql)) {
            return where;
        }
        try {
            Expression expression = CCJSqlParserUtil.parseExpression(dataFilterSql);
            // 数据权限使用单独的括号 防止与其他条件冲突
            Parenthesis parenthesis = new Parenthesis(expression);

            if (ObjectUtil.isNotNull(where)) {
                return new AndExpression(where, parenthesis);
            } else {
                return parenthesis;
            }
        } catch (JSQLParserException e) {
            throw new BusinessException("数据权限解析异常 => " + e.getMessage());
        }
    }

    /**
     * 构造数据过滤sql
     */
    private String buildDataFilter(DataColumn[] dataColumns, boolean isSelect) {
        //TODO
//        // 更新或删除需满足所有条件
//        String joinStr = isSelect ? " OR " : " AND ";
//        SysUserLoginVO user = DataPermissionHelper.getVariable("user");
//
//        StandardEvaluationContext context = new StandardEvaluationContext();
//        context.setBeanResolver(beanResolver);
//        DataPermissionHelper.getContext().forEach(context::setVariable);
//        Set<String> conditions = new HashSet<>();
//
//        if (remoteUserService == null) {
//            remoteUserService = SpringUtil.getBean(RemoteUserService.class);
//        }
//        List<SysRoleVO> roleList = remoteUserService.getRoleList(user, SecurityConstants.INNER);
//
//        for (SysRoleVO role : roleList) {
//            user.setRoleId(role.getId());
//            // 获取角色权限泛型
//            DataScopeType type = DataScopeType.findCode(role.getDataScope());
//            if (ObjectUtil.isNull(type)) {
//                throw new BusinessException("角色数据范围异常 => " + role.getDataScope());
//            }
//            // 全部数据权限直接返回
//            if (type == DataScopeType.ALL) {
//                return "";
//            }
//            boolean isSuccess = false;
//            for (DataColumn dataColumn : dataColumns) {
//                if (dataColumn.key().length != dataColumn.value().length) {
//                    throw new BusinessException("角色数据范围异常 => key与value长度不匹配");
//                }
//                // 不包含 key 变量 则不处理
//                if (!StringUtils.containsAny(type.getSqlTemplate(),
//                        Arrays.stream(dataColumn.key()).map(key -> "#" + key).toArray(String[]::new)
//                )) {
//                    continue;
//                }
//                // 设置注解变量 key 为表达式变量 value 为变量值
//                for (int i = 0; i < dataColumn.key().length; i++) {
//                    context.setVariable(dataColumn.key()[i], dataColumn.value()[i]);
//                }
//
//                // 解析sql模板并填充
//                String sql = parser.parseExpression(type.getSqlTemplate(), parserContext).getValue(context, String.class);
//                conditions.add(joinStr + sql);
//                isSuccess = true;
//            }
//            // 未处理成功则填充兜底方案
//            if (!isSuccess && StringUtils.isNotBlank(type.getElseSql())) {
//                conditions.add(joinStr + type.getElseSql());
//            }
//        }
//
//        if (CollUtil.isNotEmpty(conditions)) {
//            String sql = StreamUtils.join(conditions, Function.identity(), "");
//            return sql.substring(joinStr.length());
//        }
        return "";
    }

    private DataColumn[] findAnnotation(String mappedStatementId) {
        Map<String, DataPermission> aa = dataPermissionCacheMap;

        StringBuilder sb = new StringBuilder(mappedStatementId);
        int index = sb.lastIndexOf(".");
        String clazzName = sb.substring(0, index);
        String methodName = sb.substring(index + 1, sb.length());
        Class<?> clazz = ClassUtil.loadClass(clazzName);
        List<Method> methods = Arrays.stream(ClassUtil.getDeclaredMethods(clazz))
                .filter(method -> method.getName().equals(methodName)).collect(Collectors.toList());
        DataPermission dataPermission;
        // 获取方法注解
        for (Method method : methods) {
            dataPermission = dataPermissionCacheMap.get(mappedStatementId);
            if (ObjectUtil.isNotNull(dataPermission)) {
                return dataPermission.value();
            }
            if (AnnotationUtil.hasAnnotation(method, DataPermission.class)) {
                dataPermission = AnnotationUtil.getAnnotation(method, DataPermission.class);
                dataPermissionCacheMap.put(mappedStatementId, dataPermission);
                return dataPermission.value();
            }
        }

        dataPermission = dataPermissionCacheMap.get(clazz.getName());
        if (ObjectUtil.isNotNull(dataPermission)) {
            return dataPermission.value();
        }
        // 获取类注解
        if (AnnotationUtil.hasAnnotation(clazz, DataPermission.class)) {
            dataPermission = AnnotationUtil.getAnnotation(clazz, DataPermission.class);
            dataPermissionCacheMap.put(clazz.getName(), dataPermission);
            return dataPermission.value();
        }
        return null;
    }

    /**
     * 是否为无效方法 无数据权限
     */
    public boolean isInvalid(String mappedStatementId) {
        return invalidCacheSet.contains(mappedStatementId);
    }
}