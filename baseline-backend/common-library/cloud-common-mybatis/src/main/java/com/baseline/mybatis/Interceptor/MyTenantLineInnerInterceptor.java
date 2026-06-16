package com.baseline.mybatis.Interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.baseline.core.exception.BusinessException;
import com.baseline.mybatis.utils.ToolUtils;
import com.baseline.utils.security.SecurityUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Table;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;


@Slf4j
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MyTenantLineInnerInterceptor extends TenantLineInnerInterceptor {

    private TenantLineHandler tenantLineHandler;

    public MyTenantLineInnerInterceptor() {
        super();
    }

    public MyTenantLineInnerInterceptor(TenantLineHandler tenantLineHandler) {
        super(tenantLineHandler);
        this.tenantLineHandler = tenantLineHandler;
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        // 检查是否为特定 SQL 语句，如 CREATE STABLE，如果是则跳过
        if (ToolUtils.shouldSkipSql(boundSql.getSql())) {
            return;
        }

        super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    /**
     * 检查实体对象是否已经设置了租户ID，并验证权限
     * 如果已设置，验证该租户ID是否是当前用户的租户ID（普通租户模式只能操作自己的数据）
     * @return true表示实体已设置租户ID且有权限操作
     * @throws RuntimeException 如果设置的租户ID不是当前用户的租户ID
     */
    private boolean entityHasTenantId(Object parameter) {
        if (parameter == null) {
            log.info("【租户检查】参数对象为null");
            return false;
        }
        
        log.info("【租户检查】参数对象类型: {}", parameter.getClass().getName());
        
        try {
            MetaObject metaObject = SystemMetaObject.forObject(parameter);
            
            // 打印所有可用的getter方法
            String[] getterNames = metaObject.getGetterNames();
            log.info("【租户检查】实体可用的getter方法: {}", String.join(", ", getterNames));
            
            // 尝试多种可能的属性名
            String[] possibleNames = {"tenantId", "tenant_id", "TENANT_ID"};
            
            for (String propertyName : possibleNames) {
                if (metaObject.hasGetter(propertyName)) {
                    Object tenantIdValue = metaObject.getValue(propertyName);
                    log.info("【租户检查】找到属性 {}, 值: {}, 类型: {}", 
                            propertyName, 
                            tenantIdValue, 
                            tenantIdValue != null ? tenantIdValue.getClass().getName() : "null");
                    
                    // 如果租户ID不为空，说明已经设置了租户ID（包括0）
                    if (tenantIdValue != null) {
                        Long entityTenantId = null;
                        
                        if (tenantIdValue instanceof Long) {
                            entityTenantId = (Long) tenantIdValue;
                        } else if (tenantIdValue instanceof Integer) {
                            entityTenantId = ((Integer) tenantIdValue).longValue();
                        }
                        
                        if (entityTenantId != null) {
                            // 验证权限：普通租户模式只能操作自己的数据
                            validateTenantPermission(entityTenantId);
                            
                            log.info("【租户检查】✅ 实体已设置租户ID: {}, 且有权限操作（属性名: {}）", entityTenantId, propertyName);
                            return true;
                        } else {
                            log.info("【租户检查】属性 {} 不是Long/Integer类型", propertyName);
                        }
                    }
                } else {
                    log.debug("【租户检查】未找到属性: {}", propertyName);
                }
            }
            
            log.info("【租户检查】❌ 未检测到有效的租户ID");
        } catch (BusinessException e) {
            // 租户权限异常需要向上抛出，阻止操作
            log.warn("【租户检查】权限验证失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            // 其他异常记录日志但不影响流程
            log.warn("【租户检查】检查失败: {}", e.getMessage(), e);
        }
        
        return false;
    }
    
    /**
     * 验证租户权限：普通租户模式下，只能操作自己的数据
     * @param targetTenantId 要操作的目标租户ID
     * @throws BusinessException 如果没有权限操作该租户
     */
    private void validateTenantPermission(Long targetTenantId) {
        // 获取当前用户的租户ID
        Long currentTenantId = SecurityUtils.getTenantId();
        
        // 系统用户（租户ID为0）可以操作任何租户
        if (currentTenantId == null || currentTenantId == 0) {
            log.info("【租户权限】系统用户，允许操作任何租户");
            return;
        }
        
        // 普通租户模式：只能操作自己的数据
        if (!currentTenantId.equals(targetTenantId)) {
            String errorMsg = String.format(
                "【租户权限】❌ 无权操作租户ID=%d的数据！当前用户租户ID=%d，只能操作自己的数据", 
                targetTenantId, 
                currentTenantId
            );
            log.error(errorMsg);
            throw new BusinessException("无权操作该租户的数据，租户ID: " + targetTenantId);
        }
        
        log.info("【租户权限】✅ 有权操作租户ID={}的数据（当前租户ID={}）", targetTenantId, currentTenantId);
    }
    
    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        BoundSql boundSql = mpSh.boundSql();
        
        // 检查是否为特定 SQL 语句，如 CREATE STABLE，如果是则跳过
        if (ToolUtils.shouldSkipSql(boundSql.getSql())) {
            return;
        }
        
        MappedStatement ms = mpSh.mappedStatement();
        SqlCommandType commandType = ms.getSqlCommandType();
        
        // 对于INSERT和UPDATE操作，检查实体是否已设置租户ID
        if (commandType == SqlCommandType.INSERT || commandType == SqlCommandType.UPDATE) {
            Object parameterObject = boundSql.getParameterObject();
            
            try {
                // 如果实体已经设置了租户ID，直接返回，不调用父类的beforePrepare
                if (entityHasTenantId(parameterObject)) {
                    log.info("【租户拦截】实体已设置租户ID，跳过租户拦截器的SQL改写");
                    return;  // 直接返回，不调用super.beforePrepare()
                }
            } catch (BusinessException e) {
                // 在这里直接抛出BusinessException，避免被MyBatis包装
                throw e;
            }
        }

        // 只有在实体未设置租户ID时，才调用父类方法进行自动填充
        super.beforePrepare(sh, connection, transactionTimeout);
    }

    @Override
    public Expression buildTableExpression(final Table table, final Expression where, final String whereSegment) {
        //如果没有租户ID，则说明是系统用户，系统用户不受租户条件限制
        if(ObjectUtil.isNull(SecurityUtils.getLoginUser()) || SecurityUtils.getTenantId() == 0){
            return null;
        }

        return this.tenantLineHandler.ignoreTable(table.getName()) ? null : new EqualsTo(this.getAliasColumn(table), this.tenantLineHandler.getTenantId());
    }


}
