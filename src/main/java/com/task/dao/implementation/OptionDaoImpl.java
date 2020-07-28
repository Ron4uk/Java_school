package com.task.dao.implementation;

import com.task.dao.GenericDaoImpl;
import com.task.dao.OptionDao;
import com.task.dto.DtoEntity;
import com.task.entity.Contract;
import com.task.entity.Option;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class OptionDaoImpl extends GenericDaoImpl<Option> implements OptionDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Option> getAllWithout(Integer id) {
        Query query = entityManager.createQuery("FROM Option WHERE id != :id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public void deleteById(Object id) {
    entityManager.createNativeQuery("DELETE FROM required_options WHERE requiredOptions_id="+id).executeUpdate();
    entityManager.createNativeQuery("DELETE FROM exclusion_options WHERE exclusionOptions_id="+id).executeUpdate();
    entityManager.createNativeQuery("DELETE FROM options_in_tariff WHERE option_id="+id).executeUpdate();
     super.deleteById(id);
    }
}
