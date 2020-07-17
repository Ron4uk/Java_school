package com.task.dao.implementation;

import com.task.dao.AuthorizationDao;
import com.task.dao.GenericDaoImpl;
import com.task.entity.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Implementation of {@link AuthorizationDao}
 */

@Repository
public class AuthorizationDaoImpl extends GenericDaoImpl<Authorization> implements AuthorizationDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Authorization findByPhone(String phone) {
        Query query = entityManager.createQuery("FROM Authorization where phone = :phone");
        query.setParameter("phone", phone);

        return (Authorization) query.getSingleResult();
    }
}
