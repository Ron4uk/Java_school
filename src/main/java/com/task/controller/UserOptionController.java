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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@Getter
@Setter
@Log4j
@AllArgsConstructor(onConstructor=@__({@Autowired}))
@SessionAttributes({"contractDto","orderDto"})
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
    public String chooseNewOption(@ModelAttribute("contractDto") ContractDto contractDto, @ModelAttribute("orderDto") OrderDto orderDto){

        return  contractService.checkContract(contractDto, orderDto)? "userchoosenewoption":"user";
    }

    @PostMapping("addoptiontotariff")
    public String addOptionToTariff(@ModelAttribute("orderDto") OrderDto orderDto, @ModelAttribute("contractDto")ContractDto contractDto){
        LOGGER.info("orderDto={}", orderDto);
        return "user";
    }

    @GetMapping("/usermanageoption")
    public String manageOption(@ModelAttribute("orderDto") OrderDto orderDto, @ModelAttribute("contractDto")ContractDto contractDto){
        tariffService.createRequirementsForEmbeddedOptions(contractDto.getTariffDto());
        return contractService.checkContract(contractDto, orderDto)? "usermanageoption":"user";
    }

    @PostMapping("/changecurrentoptions")
    public String changeCurrentOptions(@ModelAttribute("orderDto") OrderDto orderDto){
        contractService.checkOrder(orderDto, "changecurrentoptions");
        LOGGER.info("orderDto={}", orderDto);
        return "user";
    }
    @GetMapping("/userdisableoptions")
    public String showOptionToDisable(){
        return "userdisableoptions";
    }

    @PostMapping("/disableoption")
    public String disableOption(@ModelAttribute("orderDto") OrderDto orderDto){
        contractService.checkOrder(orderDto, "disableoption");
        return "user";
    }
}
