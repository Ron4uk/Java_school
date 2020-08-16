package com.task.controller;

import com.task.dto.ContractDto;
import com.task.dto.OrderDto;
import com.task.service.ContractService;
import com.task.service.TariffService;
import com.task.service.impl.ContractServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Getter
@Setter
@Log4j
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping("/user")
@SessionAttributes({"contractDto", "orderDto"})
public class UserOptionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractServiceImpl.class);
    private ContractService contractService;
    private TariffService tariffService;

    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
    }

    @ModelAttribute("orderDto")
    public OrderDto getOrderDto() {
        return new OrderDto();
    }

    @GetMapping("/userchoosenewoption")
    public String chooseNewOption(@ModelAttribute("contractDto") ContractDto contractDto, @ModelAttribute("orderDto") OrderDto orderDto) {
        log.info(orderDto);
        return contractService.checkContract(contractDto, orderDto) ? "userchoosenewoption" : "user";
    }

    @PostMapping("addoptiontotariff")
    public String addOptionToTariff(@ModelAttribute("orderDto") OrderDto orderDto) {
        log.info(orderDto);
         return "user";
    }

    @GetMapping("/usermanageoption")
    public String manageOption() {
        return "usermanageoption";
    }

    @PostMapping("/connectedoptionbyuser")
    public String connectedNewOptionByUser(@ModelAttribute("contractDto") ContractDto contractDto,
                                           @RequestParam(name = "optid") Integer id, Model model){
        model.addAttribute("result", contractService.addConnectedOption(contractDto, id));
        return "usermanageoption";
    }

    @PostMapping("/disconnectoptionbyuser")
    public String disconnectOptionByUser(@ModelAttribute("contractDto") ContractDto contractDto,
                                           @RequestParam(name = "optid") Integer id, Model model){
        model.addAttribute("result", contractService.disconnectOption(contractDto, id));
        return "usermanageoption";
    }
}
