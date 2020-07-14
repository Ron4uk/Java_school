package com.task.service;

import com.task.dao.ClientDao;
import com.task.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl {
    @Autowired
    private ClientDao clientDao;

    public ClientDao getClientDao() {
        return clientDao;
    }

    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public List<Client> getAll() {
        return clientDao.getAll();
    }
}
