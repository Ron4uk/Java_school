package com.task.controller;

import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.dto.OptionDto;
import com.task.dto.TariffDto;
import com.task.service.ClientService;
import com.task.service.ContractService;
import com.task.service.TariffService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
@Getter
@Setter
@RequestMapping("/employee")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
@SessionAttributes({"clientDto", "contractDto", "optionDto", "tariffDto"})
public class ClientController {

    private ClientService clientService;
    private TariffService tariffService;
    private ContractService contractService;

    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
    }

    @GetMapping("/getAllClients")
    public String getAllClients(@RequestParam(required = false) Integer id, Model model) {
        model.addAttribute("clientList", clientService.getAllDtoByPage(id));
        model.addAttribute("countClients", clientService.countContractsInBd());
        model.addAttribute("pagenumber", id==null?0:id);
        return "employee";
    }

    @ModelAttribute("clientDto")
    public ClientDto getClientDto() {
        return new ClientDto();
    }

    @GetMapping("/newclient")
    public String newClientForm() {
        return "newclient";
    }

    @PostMapping("/addnewclient")
    public String addClient(@ModelAttribute("clientDto") ClientDto clientDto, Model model) {
        clientService.check(clientDto);
        model.addAttribute("listTariffs", tariffService.getAllDtoWithReq());
        model.addAttribute("path", "/employee/newclient");
        return "newcontract";
    }

    @GetMapping("/chooseclient")
    public String chooseClient() {
        return "chooseclient";
    }

    @PostMapping("/chooseclient")
    public String searchClient(@ModelAttribute("contractDto") ContractDto contractDto, Model model) {
        if(contractDto.getPhone()!=null) model.addAttribute("client",
                contractService.findByPhoneDto(contractDto.getPhone(), "/chooseclient").getClientDto());
        return "chooseclient";
    }

    @GetMapping("/searchclient")
    public String searchClient(Model model, HttpServletRequest request){

        model.addAttribute("clientDto", clientService.findByIdDto(request.getParameter("choice")));
        model.addAttribute("listTariffs", tariffService.getAllDtoWithReq());
        model.addAttribute("path", "/employee/chooseclient");
        return "newcontract";
    }

    @PostMapping("/findClientOnMainPage")
    public String findClientOnMainPage(@ModelAttribute("contractDto") ContractDto contractDto, Model model){
        if(contractDto.getPhone()!=null) model.addAttribute("contract",
                contractService.findByPhoneDto(contractDto.getPhone(), "/employee"));
        return "employee";
    }

    @GetMapping
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
