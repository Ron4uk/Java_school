package com.task.dao;

import com.task.entity.Client;

import java.util.List;

public interface ClientDao {
    List<Client> getAll();
    void add(Client client);
}
