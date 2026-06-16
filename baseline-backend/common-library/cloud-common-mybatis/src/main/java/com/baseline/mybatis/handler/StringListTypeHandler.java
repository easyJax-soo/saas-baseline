package com.baseline.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 *
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {

    public StringListTypeHandler(){

    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<String> strs, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, strs.stream().map(Object::toString).collect(Collectors.joining(",")));
    }


    @Override
    public List<String> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String ids = resultSet.getString(s);
        return ids == null || ids.equals("") ? null :
                Stream.of(ids.split(",")).collect(Collectors.toList());
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String ids = resultSet.getString(i);
        return ids == null || ids.equals("") ? null :
                Stream.of(ids.split(",")).collect(Collectors.toList());
    }

    @Override
    public List<String> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String ids = callableStatement.getString(i);
        return ids == null || ids.equals("") ? null :
                Stream.of(ids.split(",")).collect(Collectors.toList());
    }
}
