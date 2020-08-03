package com.task.dao.implementation;

import com.task.customeexceptions.WrongPhoneNumberException;
import com.task.dao.ContractDao;
import com.task.dao.GenericDaoImpl;
import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.entity.Contract;
import com.task.entity.Tariff;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
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
}
