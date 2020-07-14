package com.task.controller;

import com.task.dao.ClientDao;
import com.task.entity.Client;
import com.task.service.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;


@Controller
public class MainController {
    @Autowired
    private ClientServiceImpl clientService;

    public ClientServiceImpl getClientService() {
        return clientService;
    }

    public void setClientService(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }



    @GetMapping("/main")
    public String testJsp(Map<String, Object> model) {
        List<Client> clients = clientService.getAll();
        model.put("message", clients);
        return "main";
    }


}
