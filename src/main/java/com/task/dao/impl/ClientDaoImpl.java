package com.task.dao.impl;

import com.task.exception.WrongEmailException;
import com.task.exception.WrongPassportException;
import com.task.dao.ClientDao;
import com.task.dao.GenericDaoImpl;
import com.task.dto.ClientDto;
import com.task.entity.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Implementation of {@link ClientDao}
 */
@Repository
public class ClientDaoImpl extends GenericDaoImpl<Client> implements ClientDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void check(ClientDto clientDto) {
        Query query = entityManager.createNativeQuery("SELECT id from clients WHERE series= ? AND number =? ");
        query.setParameter(1, clientDto.getPassport_series());
        query.setParameter(2, clientDto.getPassport_number());
        if(!query.getResultList().isEmpty()){
            throw new WrongPassportException("This passport already exist", clientDto);
        }
        query = entityManager.createNativeQuery("SELECT id from clients WHERE email = ?");
        query.setParameter(1, clientDto.getEmail());
        if(!query.getResultList().isEmpty()){
            throw new WrongEmailException("This email already exist", clientDto);
        }
    }

    @Override
    public List<Client> getAllByPage(int skipClients, int numberContractsOnPage) {
        Query query = entityManager.createQuery("FROM Client order by lastName, firstName").
                setFirstResult(skipClients).setMaxResults(numberContractsOnPage);
        return query.getResultList();
    }

    @Override
    public Long countContractsInBd() {
        Query query = entityManager.createQuery("SELECT COUNT (id) FROM Client");
        return (Long) query.getSingleResult();
    }
}
