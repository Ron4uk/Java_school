package com.task.dao.impl;

import com.task.exception.WrongPhoneNumberException;
import com.task.dao.ContractDao;
import com.task.dao.GenericDaoImpl;
import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.entity.Contract;
import com.task.entity.Tariff;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ContractDaoImpl extends GenericDaoImpl<Contract> implements ContractDao {
    @Override
    public Contract findByPhone(String phone) {
        Query query = entityManager.createQuery("FROM Contract where phone = :phone");
        query.setParameter("phone", phone);
        List<Contract> results=query.getResultList();
        if(results.isEmpty()) return null;
        return results.get(0);

    }

    @Override
    public void check(String phone, ClientDto clientDto, ContractDto contractDto) {
        Query query = entityManager.createNativeQuery("SELECT id from contracts WHERE phone= ?");
        query.setParameter(1, phone);
        if(!query.getResultList().isEmpty()){
            throw new WrongPhoneNumberException("This phone number already exist", clientDto, contractDto);
        }
    }

    @Override
    public List<Contract> getAllWithOldTariff(Tariff tariff) {
        Query query = entityManager.createQuery("FROM Contract where tariff = :tariff");
        query.setParameter("tariff", tariff);
        return query.getResultList();
    }

    @Override
    public List<Contract> getAllByPage(int skipContracts, int numberContractsOnPage) {
        Query query = entityManager.createQuery("FROM Contract order by client.lastName, client.firstName").
                setFirstResult(skipContracts).setMaxResults(numberContractsOnPage);
        return query.getResultList();
    }

    @Override
    public Long countContractsInBd() {
        Query query = entityManager.createQuery("SELECT COUNT (id) FROM Contract ");
        return (Long) query.getSingleResult();
    }
}
