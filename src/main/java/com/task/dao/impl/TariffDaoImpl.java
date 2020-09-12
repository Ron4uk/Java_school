package com.task.dao.impl;

import com.task.dao.GenericDaoImpl;
import com.task.dao.TariffDao;
import com.task.entity.Option;
import com.task.entity.Tariff;
import javafx.print.Collation;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Log4j
public class TariffDaoImpl extends GenericDaoImpl<Tariff> implements TariffDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TariffDaoImpl.class);
    @Override
    public List<Tariff> getAllWithout(Integer id) {
        LOGGER.info("[{}]  [{}] getAllWithout id={}", LocalDateTime.now(), LOGGER.getName(), id);
        Query query = entityManager.createQuery("FROM Tariff WHERE id != :id AND deleted !=true");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Tariff> getAll() {
        LOGGER.info("[{}]  [{}] getAll ", LocalDateTime.now(), LOGGER.getName());
        Query query = entityManager.createQuery("FROM Tariff WHERE  deleted !=true");
        return query.getResultList();
    }

    @Override
    public List<Tariff> findAllTariffWithOption(Option option) {
        LOGGER.info("[{}]  [{}] findAllTariffWithOption option={}", LocalDateTime.now(), LOGGER.getName(), option);
        Query query = entityManager.createNativeQuery("SELECT tariff_id FROM options_in_tariff WHERE option_id= "+option.getId());
        List<Integer> ids = query.getResultList();
        List<Tariff> tariffs = new ArrayList<>();
        if(ids.size()>0){
            for(Integer id:ids){
                tariffs.add(findById(id));
            }
        }
        return tariffs;
    }


}
