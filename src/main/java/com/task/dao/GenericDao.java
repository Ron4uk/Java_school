package com.task.dao;

import java.util.List;

/**
 * Generic DAO for all DAO layers
 *
 */
public interface GenericDao<T> {
    T create(T o);
    T findById(Object id);
    T update (T o);
    void delete (T o);
    void deleteById (Object id);
    List<T> getAll();
}
