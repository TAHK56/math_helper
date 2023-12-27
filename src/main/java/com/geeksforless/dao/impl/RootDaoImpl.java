package com.geeksforless.dao.impl;

import com.geeksforless.dao.RootDao;
import com.geeksforless.dao.query.QueryUtil;
import com.geeksforless.entity.Equation;
import com.geeksforless.entity.Root;
import com.geeksforless.util.ConnectionSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RootDaoImpl implements RootDao {


    @Override
    public void create(Root root) {
        try (PreparedStatement statement = ConnectionSource.open().prepareStatement(QueryUtil.CREATE_ROOT)) {
            statement.setString(1, root.getViewInDb());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Root findByViewInDB(String view) {
        Root root = null;
        String query = QueryUtil.FIND_ROOT_BY_VIEW + "\'" + view + "\'";
        try (var resultSet = ConnectionSource.open().createStatement().executeQuery(query)) {
            while (resultSet.next()) {
                root = new Root();
                root.setId(resultSet.getLong("id"));
                root.setViewInDb(resultSet.getString("view"));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return root;
    }

    @Override
    public List<Root> findAll() {
        List<Root> roots = new ArrayList<>();
        try (var resultSet = ConnectionSource.open().createStatement().executeQuery(QueryUtil.FIND_ALL_ROOTS)) {
            while (resultSet.next()) {
                Root root = new Root();
                root.setId(resultSet.getLong("id"));
                root.setViewInDb(resultSet.getString("view"));
                roots.add(root);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return roots;
    }


    @Override
    public Set<Equation> findAllEquationsByRoot(String root) {
        Set<Equation> equations = new HashSet<>();
        try (var resultSet = ConnectionSource.open().createStatement()
                .executeQuery(QueryUtil.FIND_EQUATION_BY_ROOT + root)) {
            while (resultSet.next()) {
                Equation equation = new Equation();
                equation.setId(resultSet.getLong("id"));
                equation.setViewInDb(resultSet.getString("view"));
                equations.add(equation);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        findByViewInDB(root).setEquations(equations);
        return equations;
    }

    @Override
    public Set<Equation> findAllEquationsHasOneRoot(String root) {
        Set<Equation> equations = new LinkedHashSet<>();
        try (var resultSet = ConnectionSource.open().createStatement()
                .executeQuery(QueryUtil.FIND_EQUATION_HAS_ONE_ROOT + root)) {
            while (resultSet.next()) {
                Equation equation = new Equation();
                equation.setId(resultSet.getLong("id"));
                equation.setViewInDb(resultSet.getString("view"));
                equations.add(equation);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        findByViewInDB(root).setEquations(equations);
        return equations;
    }
}
