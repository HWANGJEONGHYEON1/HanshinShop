package com.hanshin.shop.config;

import com.hanshin.shop.vo.user.RoleType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleTypeHandler extends BaseTypeHandler<RoleType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, RoleType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public RoleType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return RoleType.valueOf(rs.getString(columnName));
    }

    @Override
    public RoleType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return RoleType.valueOf(rs.getString(columnIndex));
    }

    @Override
    public RoleType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return RoleType.valueOf(cs.getString(columnIndex));
    }
}
