package com.task.dao.impl;

import com.task.exception.WrongPhoneNumberException;
import com.task.dao.ContractDao;
import com.task.dao.GenericDaoImpl;
import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.entity.Contract;
import com.task.entity.Tariff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ContractDaoImpl extends GenericDaoImpl<Contract> implements ContractDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractDaoImpl.class);
    @Override
    public Contract findByPhone(String phone) {
        LOGGER.info("[{}] [{}] findByPhone: {}",LOGGER.getName(), LocalDateTime.now(), phone);
        Query query = entityManager.createQuery("FROM Contract where phone = :phone");
        query.setParameter("phone", phone);
        List<Contract> results=query.getResultList();
        if(results.isEmpty()) return null;
        return results.get(0);

    }

    @Override
    public void check(String phone, ClientDto clientDto, ContractDto contractDto) {
        LOGGER.info("[{}] [{}] check: {}",LOGGER.getName(), LocalDateTime.now(), phone);
        Query query = entityManager.createNativeQuery("SELECT id from contracts WHERE phone= ?");
        query.setParameter(1, phone);
        if(!query.getResultList().isEmpty()){
            throw new WrongPhoneNumberException("This phone number already exist", clientDto, contractDto);
        }
    }

    @Override
    public List<Contract> getAllWithOldTariff(Tariff tariff) {
        LOGGER.info("[{}] [{}] getAllWithOldTariff: {}",LOGGER.getName(), LocalDateTime.now(), tariff);
        Query query = entityManager.createQuery("FROM Contract where tariff = :tariff");
        query.setParameter("tariff", tariff);
        return query.getResultList();
    }

    @Override
    public List<Contract> getAllByPage(int skipContracts, int numberContractsOnPage) {
        LOGGER.info("[{}] [{}] getAllByPage: {} {}",LOGGER.getName(), LocalDateTime.now(), skipContracts, numberContractsOnPage);
        Query query = entityManager.createQuery("FROM Contract order by client.lastName, client.firstName").
                setFirstResult(skipContracts).setMaxResults(numberContractsOnPage);
        return query.getResultList();
    }

    @Override
    public Long countContractsInBd() {
        LOGGER.info("[{}] [{}] countContractsInBd",LOGGER.getName(), LocalDateTime.now());
        Query query = entityManager.createQuery("SELECT COUNT (id) FROM Contract ");
        return (Long) query.getSingleResult();
    }
}
