package com.baseline.mybatis.handler;


import com.baseline.core.annotation.IEnumerator;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CodeEnumTypeHandler<E extends Enum<?> & IEnumerator> extends BaseTypeHandler<IEnumerator> {

    private final Class<E> type;

    public CodeEnumTypeHandler(Class<E> type){
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    // 将java对象转为jdbcType
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, IEnumerator enumerator, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,enumerator.getCode());
    }

    // 通过字段名获取时，如何将jdbcType转为java对象
    @Override
    public IEnumerator getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int code = resultSet.getInt(s);
        return resultSet.wasNull() ? null : codeOf(code);
    }

    // 通过字段索引获取时，如何将jdbcType转为java对象
    @Override
    public IEnumerator getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int code = resultSet.getInt(i);
        return resultSet.wasNull() ? null : codeOf(code);
    }

    // 用定义调用存储过程后，如何将jdbcType转为java对象
    @Override
    public IEnumerator getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return callableStatement.wasNull() ? null : codeOf(code);
    }

    private E codeOf(int code){
        try {
            return CodeEnumTypeHandler.codeOf(type, code);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + code + " to " + type.getSimpleName() + " by code value.", ex);
        }
    }

    public static <E extends Enum<?> & IEnumerator> E codeOf(Class<E> enumClass, int code){
        E[] es=enumClass.getEnumConstants();
        for(E e:es){
            if(e.getCode()==code) {
                return e;
            }
        }
        return null;
    }
}
