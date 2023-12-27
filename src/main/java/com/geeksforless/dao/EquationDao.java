package com.geeksforless.dao;

import com.geeksforless.entity.Equation;
import com.geeksforless.entity.Root;

import java.util.Set;

public interface EquationDao extends BaseDao<Equation> {

    Set<Root> findAllRootsByEquation(String equation);
}
