package com.task.dao.impl;

import com.task.dao.GenericDaoImpl;
import com.task.dao.TariffDao;
import com.task.entity.Option;
import com.task.entity.Tariff;
import javafx.print.Collation;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.*;

@Repository
@Log4j
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

    @Override
    public List<Tariff> findAllTariffWithOption(Option option) {
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

    @Override
    public void update(List<Tariff> tariffs, Option option) {
        if(option.getRequiredOptions().size()>0){
            List<Option> listForEquals = new ArrayList<>(option.getRequiredOptions());
            Deque<Option> requiredOptions = new ArrayDeque<>();
            requiredOptions.addAll(option.getRequiredOptions());
            while (requiredOptions.size()>0){
                Option opt = requiredOptions.pollFirst();
                if(opt.getRequiredOptions().size()>0) requiredOptions.addAll(opt.getRequiredOptions());
                if(!listForEquals.contains(opt)) listForEquals.add(opt);
            }

            for(Tariff tariff: tariffs){
                if(!tariff.getOptions().containsAll(listForEquals)){
                    tariff.getOptions().addAll(listForEquals);
                    update(tariff);
                }

            }
        }
    }
}
