package com.geeksforless.entity;

import java.util.Objects;
import java.util.Set;

public class Root extends BaseEntity {

    private String viewInDb;

    private Set<Equation> equations;
    public Root() {
        super();
    }

    public Set<Equation> getEquations() {
        return equations;
    }

    public void setEquations(Set<Equation> equations) {
        this.equations = equations;
    }

    public String getViewInDb() {
        return viewInDb;
    }

    public void setViewInDb(String viewInDb) {
        this.viewInDb = viewInDb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Root root = (Root) o;
        return Objects.equals(viewInDb, root.viewInDb) && Objects.equals(equations, root.equations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(viewInDb, equations);
    }

    @Override
    public String toString() {
        return "Root{" +
               "viewInDb='" + viewInDb + '\'' +
               ", equations=" + equations +
               '}';
    }
}
