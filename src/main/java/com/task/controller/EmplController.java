package com.task.controller;

import com.task.entity.Client;
import com.task.service.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EmplController {
    @Autowired
    private ClientServiceImpl clientService;


    @GetMapping("/employee")
    public String employeePage() {

        return "employee";
    }
    @GetMapping("/startauthempl")
    public String startEmployeePage() {

        return "startauthempl";
    }

    @GetMapping("/getAllClients")
    public String getAllClients (Model model){
        List<Client> clients = clientService.getAll();
        model.addAttribute(clients);
        return "employee";
    }
}
