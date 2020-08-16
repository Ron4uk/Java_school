package com.task.dao;

import com.task.entity.Client;

import java.util.List;

/**
 * Interface DAO layer that represents of Client entity.
 */

public interface ClientDao extends GenericDao<Client> {

    void check(com.task.dto.ClientDto clientDto);

    List<Client> getAllByPage(int skipClients, int numberContractsOnPage);

    Long countContractsInBd();
}
