package com.geeksforless.entity;

import java.util.Objects;
import java.util.Set;

public class Equation extends BaseEntity {

    private String viewInDb;

    private Set<Root> roots;

    public Equation() {
        super();
    }

    public Set<Root> getRoots() {
        return roots;
    }

    public void setRoots(Set<Root> roots) {
        this.roots = roots;
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
        Equation equation = (Equation) o;
        return Objects.equals(viewInDb, equation.viewInDb) && Objects.equals(roots, equation.roots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(viewInDb, roots);
    }

    @Override
    public String toString() {
        return "Equation{" +
               "viewInDb='" + viewInDb + '\'' +
               ", roots=" + roots +
               '}';
    }
}
