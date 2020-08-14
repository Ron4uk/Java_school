package com.task.controller;

import com.task.dto.ContractDto;
import com.task.dto.OrderDto;
import com.task.service.ContractService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Getter
@Setter
@RequestMapping("/user")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
@SessionAttributes({"contractDto", "orderDto"})
public class UserContractController {
    private ContractService contractService;

    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
    }

    @ModelAttribute("orderDto")
    public OrderDto getOrderDto() {
        return new OrderDto();
    }

    @GetMapping("/usercontract")
    public String showUserContract(){
        return "usercontract";
    }

    @PostMapping("/blockcontractbyuser")
    public String blockContractByUser(@ModelAttribute("contractDto") ContractDto contractDto){
        contractService.blockByUser(contractDto);
        return "usercontract";
    }

    @PostMapping("/unblockcontractbyuser")
    public String unblockContractByUser(@ModelAttribute("contractDto") ContractDto contractDto){
        contractService.unblockByUser(contractDto);
        return "usercontract";
    }
}
