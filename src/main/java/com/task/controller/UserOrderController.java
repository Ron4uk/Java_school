package com.task.controller;

import com.task.dto.ContractDto;
import com.task.dto.OrderDto;
import com.task.service.ContractService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
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
public class UserOrderController {
    private ContractService contractService;

    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
    }

    @ModelAttribute("orderDto")
    public OrderDto getOrderDto() {
        return new OrderDto();
    }

    @GetMapping("/usershoppingcart")
    public String showShoppingCart(@ModelAttribute("contractDto") ContractDto contractDto, @ModelAttribute("orderDto") OrderDto orderDto) {
        return contractService.checkContract(contractDto, orderDto) ? "usershoppingcart" : "user";
    }

    @PostMapping("/confirmorder")
    public String confirmOrder(@ModelAttribute("contractDto") ContractDto contractDto, @ModelAttribute("orderDto") OrderDto orderDto, Model model) {
        model.addAttribute("result", contractService.confirmContractUserChanges(contractDto, orderDto));
        return "usershoppingcart";
    }

    @GetMapping("/deletetarifffromorder")
    public String deleteTariffFromOrder(@ModelAttribute("orderDto") OrderDto orderDto) {
        contractService.deleteTariffFromOrder(orderDto);
        return "usershoppingcart";
    }

    @GetMapping("/deletenewoptionfromorder")
    public String deleteNewOptionFromOrder(@ModelAttribute("orderDto") OrderDto orderDto, @RequestParam Integer id) {
        contractService.deleteOptionFromOrder(orderDto.getOptionsFromNewTariff(), id);
        return "usershoppingcart";
    }



}
