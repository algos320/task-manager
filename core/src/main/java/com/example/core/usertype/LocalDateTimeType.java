package com.example.core.usertype;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class LocalDateTimeType implements UserType<LocalDateTime> {
    @Override
    public int getSqlType() {
        return Types.BIGINT;
    }

    @Override
    public Class<LocalDateTime> returnedClass() {
        return LocalDateTime.class;
    }

    @Override
    public boolean equals(LocalDateTime localDateTime, LocalDateTime j1) {
        if(Objects.isNull(localDateTime) || Objects.isNull(j1)) {
            return false;
        }
        return localDateTime.equals(j1);
    }

    @Override
    public int hashCode(LocalDateTime localDateTime) {
        return Objects.hashCode(localDateTime);
    }

    @Override
    public LocalDateTime nullSafeGet(ResultSet resultSet, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
        Long millis = resultSet.getLong(i);
        LocalDateTime result = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
        return result;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, LocalDateTime localDateTime, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        if(Objects.isNull(localDateTime)) {
            preparedStatement.setNull(i, Types.NULL);
        } else {
            preparedStatement.setLong(i, localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli());
        }
    }

    @Override
    public LocalDateTime deepCopy(LocalDateTime localDateTime) {
        return localDateTime;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(LocalDateTime localDateTime) {
        return null;
    }

    @Override
    public LocalDateTime assemble(Serializable serializable, Object o) {
        return null;
    }

    @Override
    public LocalDateTime replace(LocalDateTime localDateTime, LocalDateTime j1, Object o) {
        return localDateTime;
    }
}
