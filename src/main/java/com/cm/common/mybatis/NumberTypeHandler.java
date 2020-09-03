package com.cm.common.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes({JdbcType.INTEGER, JdbcType.BIGINT, JdbcType.DOUBLE, JdbcType.FLOAT})
@MappedTypes({Number.class})
public class NumberTypeHandler implements TypeHandler<Number> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Number parameter, JdbcType jdbcType) throws SQLException {
        //TODO
    }

    @Override
    public Number getResult(ResultSet rs, String columnName) throws SQLException {
        return (Number)rs.getObject(columnName);
    }

    @Override
    public Number getResult(ResultSet rs, int columnIndex) throws SQLException {
        return (Number)rs.getObject(columnIndex);
    }

    @Override
    public Number getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return (Number)cs.getObject(columnIndex);
    }
}
