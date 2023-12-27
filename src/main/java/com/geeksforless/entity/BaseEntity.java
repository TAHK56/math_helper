package com.geeksforless.entity;

public abstract class BaseEntity {

    private Long id;

    protected BaseEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
