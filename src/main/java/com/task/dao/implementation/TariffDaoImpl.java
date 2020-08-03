package com.task.dao.implementation;

import com.task.dao.GenericDaoImpl;
import com.task.dao.TariffDao;
import com.task.entity.Tariff;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TariffDaoImpl extends GenericDaoImpl<Tariff> implements TariffDao {
    @Override
    public List<Tariff> getAllWithout(Integer id) {
        Query query = entityManager.createQuery("FROM Tariff WHERE id != :id");
        query.setParameter("id", id);
        return query.getResultList();
    }


}
