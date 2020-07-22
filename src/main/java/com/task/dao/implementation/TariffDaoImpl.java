package com.task.dao.implementation;

import com.task.dao.GenericDaoImpl;
import com.task.dao.TariffDao;
import com.task.entity.Tariff;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TariffDaoImpl extends GenericDaoImpl<Tariff> implements TariffDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tariff> getAll() {
        return entityManager.createQuery("from Tariff order by deprecated").getResultList();
    }
}
