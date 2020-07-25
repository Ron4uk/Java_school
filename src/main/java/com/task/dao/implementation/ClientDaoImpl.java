package com.task.dao.implementation;

import com.task.dao.ClientDao;
import com.task.dao.GenericDaoImpl;
import com.task.entity.Client;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link ClientDao}
 */
@Repository
public class ClientDaoImpl extends GenericDaoImpl<Client> implements ClientDao {

}
