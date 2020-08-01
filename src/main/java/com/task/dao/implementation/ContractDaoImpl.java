package com.task.dao.implementation;

import com.task.customeexceptions.WrongPhoneNumberException;
import com.task.dao.ContractDao;
import com.task.dao.GenericDaoImpl;
import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.entity.Contract;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class ContractDaoImpl extends GenericDaoImpl<Contract> implements ContractDao {
    @Override
    public Contract findByPhone(String phone) {
        Query query = entityManager.createQuery("FROM Contract where phone = :phone");
        query.setParameter("phone", phone);

        try {
            return (Contract) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void check(String phone, ClientDto clientDto, ContractDto contractDto) {
        Query query = entityManager.createNativeQuery("SELECT id from contracts WHERE phone= ?");
        query.setParameter(1, phone);
        if(!query.getResultList().isEmpty()){
            throw new WrongPhoneNumberException("This phone number already exist", clientDto, contractDto);
        }
    }
}
