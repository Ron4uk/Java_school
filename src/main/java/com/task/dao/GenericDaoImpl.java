package com.task.dao;

import com.task.exception.ExceptionsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementations of {@link GenericDao}
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericDaoImpl.class);

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
        LOGGER.info("[{}] [{}] create: {}",LOGGER.getName(), LocalDateTime.now(), o);
        entityManager.persist(o);
        return o;
    }

    @Override
    public T findById(Object id) {
        LOGGER.info("[{}] [{}] findById: {}",LOGGER.getName(), LocalDateTime.now(), id);
        return (T) entityManager.find(type, id);
    }

    @Override
    public T update(T o) {
        LOGGER.info("[{}] [{}] update: {}",LOGGER.getName(), LocalDateTime.now(), o);
        return entityManager.merge(o);
    }

    @Override
    public void delete(T o) {
        LOGGER.info("[{}] [{}] delete: {}",LOGGER.getName(), LocalDateTime.now(), o);
        entityManager.remove(o);
    }

    @Override
    public void deleteById(Object id) {
        LOGGER.info("[{}] [{}] deleteById: {}",LOGGER.getName(), LocalDateTime.now(), id);
        T entity = findById(id);
        delete(entity);
    }

    @Override
    public List<T> getAll() {
        LOGGER.info("[{}] [{}] getAll", LOGGER.getName(), LocalDateTime.now());
        return entityManager.createQuery("from " + type.getName()).getResultList();
    }


}
