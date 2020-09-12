package com.task.dao.impl;

import com.task.dao.GenericDaoImpl;
import com.task.dao.OptionDao;
import com.task.entity.Option;
import com.task.entity.Tariff;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j
public class OptionDaoImpl extends GenericDaoImpl<Option> implements OptionDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(OptionDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Option> getAllWithout(Integer id) {
        LOGGER.info("[{}]  [{}] getAllWithout id={}", LocalDateTime.now(), LOGGER.getName(), id);
        Query query = entityManager.createQuery("FROM Option WHERE id != :id AND deleted !=true");
        query.setParameter("id", id);
        return query.getResultList();
    }




    @Override
    public void delete(Option option) {
        LOGGER.info("[{}]  [{}] deleteById id={}", LocalDateTime.now(), LOGGER.getName(), option.getId());
        entityManager.createNativeQuery("DELETE FROM required_options WHERE requiredOptions_id=" + option.getId()+" or option_id="+ option.getId()).executeUpdate();
        entityManager.createNativeQuery("DELETE FROM exclusion_options WHERE exclusionOptions_id=" + option.getId()).executeUpdate();
        update(option);
    }



    @Override
    public List<Option> getAllParent(Integer id) {
        LOGGER.info("[{}]  [{}] getAllParent id={}", LocalDateTime.now(), LOGGER.getName(), id);
        Query query = entityManager.createNativeQuery("SELECT Option_id FROM required_options WHERE requiredOptions_id =" + id);
        List<Integer> optionsId = query.getResultList();
        List<Option> optionList = new ArrayList<>();
        if(!optionsId.isEmpty()){
            for(Integer optId: optionsId){
                Option option = findById(optId);
                optionList.add(option);
            }
            return optionList;
        }
        else  return new ArrayList<>();
    }

    @Override
    public Option findById(Object id) {
        LOGGER.info("[{}]  [{}] findById id={}", LocalDateTime.now(), LOGGER.getName(), id);
        Query query = entityManager.createQuery("FROM Option WHERE id =:id AND deleted !=true");
        query.setParameter("id", id);
        return (Option) query.getSingleResult();

    }

    @Override
    public Option findByIdWithDeleted(Integer id) {
        LOGGER.info("[{}]  [{}] findByIdWithDeleted id={}", LocalDateTime.now(), LOGGER.getName(), id);
        Query query = entityManager.createQuery("FROM Option WHERE id =:id");
        query.setParameter("id", id);
        return (Option) query.getSingleResult();
    }

    @Override
    public List<Option> getAll() {
        LOGGER.info("[{}]  [{}] getAll ", LocalDateTime.now(), LOGGER.getName());
        Query query = entityManager.createQuery("FROM Option WHERE deleted !=true");
        return query.getResultList();
    }

    @Override
    public List<Option> getAllWithDeleted() {
        return super.getAll();
    }

    @Override
    public Option findByName(String name) {
        LOGGER.info("[{}]  [{}] findByIdWithDeleted name = {}", LocalDateTime.now(), LOGGER.getName(), name);
        Query query = entityManager.createQuery("FROM Option WHere name =:name");
        query.setParameter("name", name);
        return query.getResultList().size()>0? (Option) query.getResultList().get(0):null;
    }
}
