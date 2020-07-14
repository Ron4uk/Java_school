package com.task.dao;

import java.util.List;

public interface GenericDao<T> {
    T create(T o);
    T find(Object id);
    T update (T o);
    void delete (T o);
    void deleteById (Object id);
    List<T> getAll();
}
