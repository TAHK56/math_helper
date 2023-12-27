package com.geeksforless.dao;

import com.geeksforless.entity.Equation;
import com.geeksforless.entity.Root;

import java.util.Set;

public interface RootDao extends BaseDao<Root> {

    Set<Equation> findAllEquationsByRoot(String root);

    Set<Equation> findAllEquationsHasOneRoot(String root);
}
