package com.task.dao;

import com.task.entity.Client;

/**
 * Interface DAO layer that represents of Client entity.
 */

public interface ClientDao extends GenericDao<Client> {

    void check(com.task.dto.ClientDto clientDto);
}
