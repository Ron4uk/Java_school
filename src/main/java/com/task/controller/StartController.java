package com.task.controller;

import com.task.dto.DtoEntity;
import com.task.service.ClientService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Controller
public class StartController {




    @GetMapping("/startauthclient")
    public String afterAuthenticationClient() {

        return "startauthclient";
    }
    @GetMapping("/")
    public String start() {

        return "startpage";
    }







}
