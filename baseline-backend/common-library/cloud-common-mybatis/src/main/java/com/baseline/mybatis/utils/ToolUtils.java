package com.baseline.mybatis.utils;

public class ToolUtils {
    /**
     * 检查是否需要跳过某些特定 SQL 语句
     * 比如 CREATE STABLE 等
     *
     * @param sql 要执行的 SQL
     * @return 如果应该跳过则返回 true
     */
    public static boolean shouldSkipSql(String sql) {
        // 简单的例子，根据 SQL 前缀跳过处理
        String trimmedSql = sql.trim().toLowerCase();
        return trimmedSql.startsWith("create");
    }
}
