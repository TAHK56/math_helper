package com.geeksforless.util;

import com.geeksforless.dao.query.QueryUtil;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionSource {

    private static final String USERNAME = "db.username";
    private static final String PASSWORD = "db.password";
    private static final String URL = "db.url";
    private static final String DRIVER = "db.driver-class-name";

    private static final String DB_NAME = "db.name";

    private ConnectionSource() {
    }

    static {
        loadDriver();
        createDatabase();
        createTables();
    }

    public static Connection open() {
        try {

            return DriverManager.getConnection(PropertiesUtil.get(URL) + PropertiesUtil.get(DB_NAME),
                    PropertiesUtil.get(USERNAME),
                    PropertiesUtil.get(PASSWORD));
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static void createDatabase() {
        try {
            var scriptRunner = new ScriptRunner(DriverManager.getConnection(PropertiesUtil.get(URL),
                    PropertiesUtil.get(USERNAME),
                    PropertiesUtil.get(PASSWORD)));
            Reader reader = new BufferedReader(new InputStreamReader(ConnectionSource.class.getClassLoader()
                    .getResourceAsStream("math_helper.sql")));
            scriptRunner.setLogWriter(null);
            scriptRunner.runScript(reader);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static void createTables() {
        try (var statement = open().createStatement()) {
            statement.executeUpdate(QueryUtil.CREATE_TABLE_EQUATIONS);
            statement.executeUpdate(QueryUtil.CREATE_TABLE_ROOTS);
            statement.executeUpdate(QueryUtil.CREATE_TABLE_EQUATIONS_ROOTS);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName(PropertiesUtil.get(DRIVER));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
