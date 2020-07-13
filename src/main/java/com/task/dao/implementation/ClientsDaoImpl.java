package com.task.dao.implementation;

import com.task.dao.ClientsDao;
import com.task.entity.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public class ClientsDaoImpl implements ClientsDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientsDaoImpl.class);
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public List<Client> getAll() {

        LOGGER.info("[{}] Connect to DB. sessionFactory is {}", LocalDateTime.now() ,sessionFactory==null? "null":"work");
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("FROM Client ");
        List<Client> clients = query.list();

        session.close();

        return clients;


    }

    @Override
    public void add(Client client) {

    }
}
