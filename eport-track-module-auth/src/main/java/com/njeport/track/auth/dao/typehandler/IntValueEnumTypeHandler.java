package com.njeport.track.auth.dao.typehandler;

import com.njeport.track.common.enums.IntValueEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class IntValueEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private final Map<Integer, E> map = new HashMap<>();

    public IntValueEnumTypeHandler(Class<E> type) {
        if (type == null) throw new IllegalArgumentException("Type argument cannot be null");

        E[] enums = type.getEnumConstants();
        if (enums == null)
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        for (E e : enums) {
            IntValueEnum intValueEnum = (IntValueEnum) e;
            map.put(intValueEnum.intValue(), e);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        IntValueEnum valuedEnum = (IntValueEnum) parameter;
        ps.setInt(i, valuedEnum.intValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return map.get(value);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return map.get(value);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return map.get(value);
        }
    }

}
