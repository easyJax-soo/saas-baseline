package com.baseline.mybatis.Interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baseline.mybatis.utils.ToolUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;

import java.sql.Connection;

public class MyBlockAttackInnerInterceptor extends BlockAttackInnerInterceptor {

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        BoundSql boundSql = mpSh.boundSql();
        // 检查是否为特定 SQL 语句，如 CREATE STABLE，如果是则跳过
        if (ToolUtils.shouldSkipSql(boundSql.getSql())) {
            return;
        }

        super.beforePrepare(sh, connection, transactionTimeout);
    }


}
