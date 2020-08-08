package com.task.controller;

import com.task.dto.ContractDto;
import com.task.dto.OptionDto;
import com.task.dto.OrderDto;
import com.task.dto.TariffDto;
import com.task.entity.Option;
import com.task.entity.Tariff;
import com.task.service.ContractService;
import com.task.service.TariffService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@Getter
@Setter
@AllArgsConstructor(onConstructor=@__({@Autowired}))
@SessionAttributes({"contractDto","orderDto"})
public class UserTariffController {

    private TariffService tariffService;
    private ContractService contractService;

    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
    }

    @ModelAttribute("orderDto")
    public OrderDto getOrderDto() {
        return new OrderDto();
    }

    @GetMapping("/userchoosenewtariff")
    public String chooseNewTariff(@ModelAttribute("contractDto") ContractDto contractDto, @ModelAttribute("orderDto") OrderDto orderDto, Model model){
        model.addAttribute("listTariffs", tariffService.getAllWithoutDto(contractDto.getTariffDto().getId()));
        return  contractService.checkContract(contractDto, orderDto)? "userchoosenewtariff":"user";
    }

    @PostMapping("/addtarifftoorder")
    public String addTariffToOrder(@ModelAttribute("orderDto") OrderDto orderDto, Model model){
        tariffService.createRequirementsForEmbeddedOptions(orderDto.getTariffDto());
        contractService.checkOrder(orderDto, "addtarifftoorder");
        model.addAttribute("listOptions", orderDto.getTariffDto().getOptions());
        return "userchoosenewoption";
    }




}
