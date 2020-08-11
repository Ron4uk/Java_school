package com.task.dao.impl;

import com.task.dao.GenericDaoImpl;
import com.task.dao.TariffDao;
import com.task.entity.Tariff;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class TariffDaoImpl extends GenericDaoImpl<Tariff> implements TariffDao {
    @Override
    public List<Tariff> getAllWithout(Integer id) {
        Query query = entityManager.createQuery("FROM Tariff WHERE id != :id AND deleted !=true");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Tariff> getAll() {
        Query query = entityManager.createQuery("FROM Tariff WHERE  deleted !=true");
        return query.getResultList();
    }
}
