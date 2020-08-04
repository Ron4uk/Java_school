package com.task.controller;

import com.task.dto.*;
import com.task.entity.Tariff;
import com.task.service.ClientService;
import com.task.service.ContractService;
import com.task.service.OptionService;
import com.task.service.TariffService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@SessionAttributes({"tariffDto", "optionDto", "clientDto", "contractDto"})
@Controller
@Getter
@Setter(onMethod = @__({@Autowired}))
@RequiredArgsConstructor()
public class EmplController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmplController.class);


    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
    }

    @GetMapping("/employee")
    public String employeePage(SessionStatus sessionStatus, Model model) {
        sessionStatus.setComplete();
        model.addAttribute("optionDto", new OptionDto());
        model.addAttribute("tariffDto", new TariffDto());
        model.addAttribute("clientDto", new ClientDto());
        model.addAttribute("contractDto", new ContractDto());
        return "employee";
    }

    @GetMapping("/startauthempl")
    public String startEmployeePage() {

        return "startauthempl";
    }









}
