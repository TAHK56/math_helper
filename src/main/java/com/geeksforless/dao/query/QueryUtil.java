package com.geeksforless.dao.query;

public final class QueryUtil {

    private QueryUtil() {
    }
    public static final String CREATE_TABLE_EQUATIONS = """
            CREATE TABLE IF NOT EXISTS equations (
            id BIGINT  NOT NULL AUTO_INCREMENT,
            view VARCHAR(255) UNIQUE,
            PRIMARY KEY (id)
            );
            """;
    public static final String CREATE_TABLE_ROOTS = """
            CREATE TABLE IF NOT EXISTS roots (
            id BIGINT  NOT NULL AUTO_INCREMENT,
            view VARCHAR(255) UNIQUE,
            PRIMARY KEY (id)
            );
            """;
    public static final String CREATE_TABLE_EQUATIONS_ROOTS = """
            CREATE TABLE IF NOT EXISTS equations_roots (
            eq_id BIGINT ,
            rt_id BIGINT,
            FOREIGN KEY (eq_id) REFERENCES equations (id),
            FOREIGN KEY (rt_id) REFERENCES roots (id)
            );
            """;

    public static final String CREATE_EQUATION = "INSERT INTO equations VALUES(default, ?)";
    public static final String CREATE_ROOT = "INSERT INTO roots VALUES(default, ?)";
    public static final String CREATE_EQUATION_ROOT = "INSERT INTO equations_roots VALUES(?, ?)";

    public static final String FIND_EQUATION_BY_ROOT = """
            SELECT equations.id, equations.view FROM equations
            INNER JOIN equations_roots ON equations.id = eq_id
            INNER JOIN roots ON roots.id = rt_id
            WHERE roots.view = 
            """;
    public static final String FIND_ROOT_BY_EQUATION = """
            SELECT roots.id, roots.view FROM equations
            INNER JOIN equations_roots ON equations.id = eq_id
            INNER JOIN roots ON roots.id = rt_id
            WHERE equations.view = 
            """;

    public static final String FIND_EQUATION_HAS_ONE_ROOT = """
            WITH one_roots_table AS (SELECT equations.id, equations.view, roots.view AS root FROM equations
                        INNER JOIN equations_roots ON equations.id = eq_id
                        INNER JOIN roots ON roots.id = rt_id
                        GROUP BY equations.view
                        HAVING COUNT(equations.id) = 1)
            SELECT id, view FROM one_roots_table
            WHERE root = 
            """;
    public static final String FIND_ALL_EQUATIONS = "SELECT * FROM equations";

    public static final String FIND_EQUATION_BY_VIEW = "SELECT * FROM equations WHERE view = ";

    public static final String FIND_ROOT_BY_VIEW = "SELECT * FROM roots WHERE view = ";

    public static final String FIND_ALL_ROOTS = "SELECT * FROM roots";
}
