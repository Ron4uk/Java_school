package com.task.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Implementations of {@link GenericDao}
 *
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {


    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> type;


        public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public T create(T o) {
        entityManager.persist(o);
        return o;
    }

    @Override
    public T findById(Object id) {

        return (T) entityManager.find(type, id);
    }

    @Override
    public T update(T o) {
        return entityManager.merge(o);
    }

    @Override
    public void delete(T o) {
        entityManager.remove(o);
    }

    @Override
    public void deleteById(Object id) {
        T entity = findById(id);
        delete(entity);
    }

    @Override
    public List<T> getAll() {

        return entityManager.createQuery("from "+ type.getName()).getResultList();
    }


}
