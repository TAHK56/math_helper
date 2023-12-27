package com.geeksforless.dao;

import com.geeksforless.entity.BaseEntity;

import java.util.List;

public interface BaseDao <E extends BaseEntity> {

    void create(E e);
    E findByViewInDB(String view);
    List<E> findAll();
}
