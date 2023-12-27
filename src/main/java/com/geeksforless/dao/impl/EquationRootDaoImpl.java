package com.geeksforless.dao.impl;

import com.geeksforless.dao.EquationRootDao;
import com.geeksforless.dao.query.QueryUtil;
import com.geeksforless.util.ConnectionSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class EquationRootDaoImpl implements EquationRootDao<Long> {

    @Override
    public void create(Long idEq, Long idRt) {
        try (PreparedStatement statement = ConnectionSource.open().prepareStatement(QueryUtil.CREATE_EQUATION_ROOT)) {
            statement.setLong(1, idEq);
            if (Objects.isNull(idRt)) {
                statement.setNull(2, Types.BIGINT);
            } else {
                statement.setLong(2, idRt);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
