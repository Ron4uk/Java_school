package com.task.controller;

import com.task.dto.ClientDto;
import com.task.dto.DtoEntity;
import com.task.entity.Client;
import com.task.service.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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



    @GetMapping("/client")
    public String clientPage (Map<String, Object> model) {
        List<DtoEntity> clients = clientService.getAllDto();
        model.put("message", clients);
        return "client";
    }



    @GetMapping("/login")
    public String signIn(@RequestParam(name = "error", required = false) Boolean error, Model model) {
        if(Boolean.TRUE.equals(error)) model.addAttribute("error", true);
        return "login";
    }
    @GetMapping("/startauthclient")
    public String afterAuthenticationClient() {

        return "startauthclient";
    }
    @GetMapping("/")
    public String start() {

        return "startpage";
    }







}
