package com.task.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j
public class LoginController {

    @GetMapping("/login")
    public String signIn(@RequestParam(name = "error", required = false) Boolean error, Model model) {
        if(Boolean.TRUE.equals(error)) model.addAttribute("error", true);
        return "login";
    }

}
