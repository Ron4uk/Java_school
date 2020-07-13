package com.task.controller;

import com.task.dao.ClientsDao;
import com.task.dao.implementation.ClientsDaoImpl;
import com.task.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;


@Controller
public class MainController {
    private ClientsDao clientsDao;

    public ClientsDao getClientsDao() {
        return clientsDao;
    }

    @Autowired
    public void setClientsDao(ClientsDao clientsDao) {
        this.clientsDao = clientsDao;
    }

    @GetMapping("/main")
    public String testJsp(Map<String, Object> model) {

        List<Client> clients = clientsDao.getAll();

//        Queue<String> myList = new ArrayDeque<>();
//        myList.add("1");
//        myList.add("2");
//        myList.add("3");
//        String string = "Message from string";
        model.put("message", clients);

        return "main";
    }


}
