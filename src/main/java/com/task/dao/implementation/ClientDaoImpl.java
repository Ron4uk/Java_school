package com.task.dao.implementation;

import com.task.dao.ClientDao;
import com.task.dao.GenericDaoImpl;
import com.task.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link ClientDao}
 */
@Repository
public class ClientDaoImpl extends GenericDaoImpl<Client> implements ClientDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDaoImpl.class);





}
