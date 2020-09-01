package com.task.controller;

import com.task.service.TariffService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Getter
@Setter
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class StartController {

    private TariffService tariffService;

    @GetMapping("/")
    public String start() {
        tariffService.sendTariffsToQueue();
        return "startpage";
    }




}
