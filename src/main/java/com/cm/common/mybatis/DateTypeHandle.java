package com.cm.common.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@MappedJdbcTypes(JdbcType.INTEGER)
public class DateTypeHandle extends BaseTypeHandler<Date> {

	@Override
	public Date getNullableResult(ResultSet rs, String name)
			throws SQLException {
		return new Date(rs.getLong(name));
	}

	@Override
	public Date getNullableResult(ResultSet rs, int index) throws SQLException {
		return new Date(rs.getLong(index));
	}

	@Override
	public Date getNullableResult(CallableStatement cs, int index)
			throws SQLException {
		return new Date(cs.getLong(index));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int index,
			Date date, JdbcType type) throws SQLException {
		ps.setLong(index, date.getTime());
	}
	
}
