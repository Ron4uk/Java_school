package com.task.dao.implementation;

import com.task.dao.ContractDao;
import com.task.dao.GenericDaoImpl;
import com.task.entity.Contract;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class ContractDaoImpl extends GenericDaoImpl<Contract> implements ContractDao {
    @Override
    public Contract findByPhone(String phone) {
        Query query = entityManager.createQuery("FROM Contract where phone = :phone");
        query.setParameter("phone", phone);

        try {
            return (Contract) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
