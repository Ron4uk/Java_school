package com.task.dao.implementation;

import com.task.dao.ClientsDao;
import com.task.entity.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class ClientsDaoImpl implements ClientsDao {

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

        Session session = sessionFactory.openSession();

        Query query = session.createQuery("FROM Client ");
        List<Client> clients = query.list();

        session.close();
        
        return clients;


    }

    @Override
    public void add(Client client) {
        //open session with a transaction
//        openTransactionSession();
//
//        Session session = getSession();
//        session.save(client);
//
//        //close session with a transaction
//        closeTransactionSesstion();
    }
}
