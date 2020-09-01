package com.task.controller;

import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.dto.OptionDto;
import com.task.dto.TariffDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@SessionAttributes({"tariffDto", "optionDto", "clientDto", "contractDto"})
@Controller
@Getter
@Setter
@AllArgsConstructor(onConstructor=@__({@Autowired}))
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
