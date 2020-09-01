package com.task.dao;

import com.task.dto.ClientDto;
import com.task.entity.Client;

import java.util.List;

/**
 * Interface DAO layer that represents of Client entity.
 */

public interface ClientDao extends GenericDao<Client> {

    void checkPassport(ClientDto clientDto);
    void checkEmail(ClientDto clientDto);

    List<Client> getAllByPage(int skipClients, int numberContractsOnPage);

    Long countContractsInBd();
}
