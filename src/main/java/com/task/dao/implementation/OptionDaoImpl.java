package com.task.dao.implementation;

import com.task.dao.GenericDaoImpl;
import com.task.dao.OptionDao;
import com.task.entity.Option;
import com.task.service.implementation.OptionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class OptionDaoImpl extends GenericDaoImpl<Option> implements OptionDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(OptionDaoImpl.class);
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
        entityManager.createNativeQuery("DELETE FROM connected_options WHERE option_id=" + id).executeUpdate();
        entityManager.createNativeQuery("DELETE FROM required_options WHERE requiredOptions_id=" + id).executeUpdate();
        entityManager.createNativeQuery("DELETE FROM exclusion_options WHERE exclusionOptions_id=" + id).executeUpdate();
        entityManager.createNativeQuery("DELETE FROM options_in_tariff WHERE option_id=" + id).executeUpdate();
        super.deleteById(id);
    }


    @Override
    public List<Option> getAllParent(Integer id) {
        Query query = entityManager.createNativeQuery("SELECT Option_id FROM required_options WHERE requiredOptions_id=" + id);
        List<Integer> optionsId = query.getResultList();
        List<Option> optionList = new ArrayList<>();
        LOGGER.info("[{}],  optionsId = {} optionsId.isEmpty={}", LocalDateTime.now(), optionsId.toString(), optionsId.isEmpty());
        if(!optionsId.isEmpty()){
            for(Integer optId: optionsId){
                LOGGER.info("[{}],  optId = {}", LocalDateTime.now(), optId);
                Option option = findById(optId);
                optionList.add(option);
            }
            return optionList;
        }
        else  return null;
    }
}
