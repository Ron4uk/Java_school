package com.task.controller;

import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.service.ClientService;
import com.task.service.ContractService;
import com.task.service.TariffService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@Getter
@Setter
@AllArgsConstructor(onConstructor=@__({@Autowired}))
@SessionAttributes({"clientDto", "contractDto"})
public class ClientController {

    private ClientService clientService;
    private TariffService tariffService;
    private ContractService contractService;

    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
    }

    @GetMapping("/getAllClients")
    public String getAllClients(Model model) {
        model.addAttribute("clientList", clientService.getAllDto());
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
        model.addAttribute("path", "/newclient");
        return "newcontract";
    }

    @GetMapping("/chooseclient")
    public String chooseClient() {
        return "chooseclient";
    }

    @PostMapping("/chooseclient")
    public String searchClient(@ModelAttribute("contractDto") ContractDto contractDto, Model model) {

        if(contractDto.getPhone()!=null) model.addAttribute("client", contractService.findByPhoneDto(contractDto.getPhone(), "chooseclient").getClientDto());

        return "chooseclient";
    }

    @GetMapping("/searchclient")
    public String searchClient(Model model, HttpServletRequest request){

        model.addAttribute("clientDto", clientService.findByIdDto(request.getParameter("choice")));
        model.addAttribute("listTariffs", tariffService.getAllDtoWithReq());
        model.addAttribute("path", "/chooseclient");
        return "newcontract";
    }

    @PostMapping("/findClientOnMainPage")
    public String findClientOnMainPage(@ModelAttribute("contractDto") ContractDto contractDto, Model model){
        if(contractDto.getPhone()!=null) model.addAttribute("contract", contractService.findByPhoneDto(contractDto.getPhone(), "employee"));
        return "employee";
    }
}
