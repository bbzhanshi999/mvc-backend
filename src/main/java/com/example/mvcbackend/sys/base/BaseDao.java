package com.example.mvcbackend.sys.base;

import java.util.List;

public interface BaseDao<T extends BaseModel> {

    T find(T condition);

    T findById(Long id);

    List<T> findList(T condition);

    List<T> findAll();

    void insert(T target);

    void update(T target);

    void delete(Long id);

    void delete(T target);
}
