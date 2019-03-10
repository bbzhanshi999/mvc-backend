package com.example.mvcbackend.sys.base;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseService<E extends BaseModel, D extends BaseDao<E>> {

    @Autowired
    protected D dao;

    public E find(E condition) {
        return dao.find(condition);
    }

    public E findById(Long id) {
        return dao.findById(id);
    }

    public List<E> findList(E condition) {
        return dao.findList(condition);
    }

    public List<E> findAll() {
        return dao.findAll();
    }

    public void insert(E target) {
        dao.insert(target);
    }

    public void update(E target) {
        dao.update(target);
    }

    public void delete(Long id) {
        dao.delete(id);
    }


    public void delete(E target) {
        dao.delete(target);
    }

    public void save(E target){
        if(target.getId()!=null){
            update(target);
        }else{
            insert(target);
        }
    }
}
