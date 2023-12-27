package com.geeksforless.dao.impl;

import com.geeksforless.dao.EquationDao;
import com.geeksforless.dao.query.QueryUtil;
import com.geeksforless.entity.Equation;
import com.geeksforless.entity.Root;
import com.geeksforless.util.ConnectionSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EquationDaoImpl implements EquationDao {

    @Override
    public void create(Equation equation) {
        try (PreparedStatement statement = ConnectionSource.open().prepareStatement(QueryUtil.CREATE_EQUATION)) {
            statement.setString(1, equation.getViewInDb());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Equation findByViewInDB(String view) {
        Equation equation = null;
        String query = QueryUtil.FIND_EQUATION_BY_VIEW + "\'" + view + "\'";
        try (ResultSet resultSet = ConnectionSource.open().createStatement().executeQuery(query)) {
            while (resultSet.next()) {
                equation = new Equation();
                equation.setId(resultSet.getLong("id"));
                equation.setViewInDb(resultSet.getString("view"));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return equation;
    }

    @Override
    public List<Equation> findAll() {
        List<Equation> equations = new ArrayList<>();
        try (ResultSet resultSet = ConnectionSource.open().createStatement().executeQuery(QueryUtil.FIND_ALL_EQUATIONS)) {
            while (resultSet.next()) {
                Equation equation = new Equation();
                equation.setId(resultSet.getLong("id"));
                equation.setViewInDb(resultSet.getString("view"));
                equations.add(equation);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return equations;
    }

    @Override
    public Set<Root> findAllRootsByEquation(String equation) {
        Set<Root> roots = new HashSet<>();
        try (ResultSet resultSet = ConnectionSource.open().createStatement()
                .executeQuery(QueryUtil.FIND_ROOT_BY_EQUATION + equation)) {
            while (resultSet.next()) {
                Root root = new Root();
                root.setId(resultSet.getLong("id"));
                root.setViewInDb(resultSet.getString("view"));
                roots.add(root);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        findByViewInDB(equation).setRoots(roots);
        return roots;
    }
}
