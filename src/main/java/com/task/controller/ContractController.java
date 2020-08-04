package com.task.controller;

import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.service.ContractService;
import com.task.service.OptionService;
import com.task.service.TariffService;
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
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
@Getter
@Setter(onMethod = @__({@Autowired}))
@RequiredArgsConstructor()
@SessionAttributes({"contractDto", "clientDto"})
public class ContractController {

    private ContractService contractService;
    private TariffService tariffService;
    private OptionService optionService;

    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
    }
    @ModelAttribute("clientDto")
    public ClientDto getClientDto() {
        return new ClientDto();
    }

    @GetMapping("/newcontract")
    public String newContract() {

        return "newcontract";
    }

    @PostMapping("/newcontract")
    public String addContract(@ModelAttribute("clientDto") ClientDto clientDto, @ModelAttribute("contractDto") ContractDto contractDto, HttpServletRequest request, Model model, SessionStatus sessionStatus) {
        contractService.create(contractDto, clientDto, request.getParameter("chosentariff"), request.getParameterValues(request.getParameter("chosentariff")));
        sessionStatus.setComplete();
        model.addAttribute("contractDto", new ContractDto());
        model.addAttribute("result", "New contract added");
        return "employee";
    }



    @PostMapping("/blockcontract")
    public String blockContract(HttpServletRequest request, Model model){
        model.addAttribute("contract", contractService.block(request.getParameter("block")));
        model.addAttribute("result", "Client blocked!");
        return "employee";
    }

    @PostMapping("/unblockcontract")
    public String unBlockContract(HttpServletRequest request, Model model){
        model.addAttribute("contract", contractService.unblock(request.getParameter("unblock")));
        model.addAttribute("result", "Client unblocked!");
        return "employee";
    }

    @GetMapping("/editcontract")
    public String editGetContract(HttpServletRequest request, Model model){
        model.addAttribute("contractDto", contractService.findByIdDto(request.getParameter("id")));
        model.addAttribute("listTariffs", tariffService.getAllDtoWithReq());
        model.addAttribute("optionsList", optionService.getAllDtoWithReqId());
        return "editcontract";
    }

    @PostMapping("/editcontract")
    public String editPostContract(@ModelAttribute("contractDto") ContractDto contractDto, HttpServletRequest request, Model model){
        model.addAttribute("contractDto", contractService.update(contractDto, request.getParameterValues(contractDto.getTariffDto().getId().toString())));
        model.addAttribute("listTariffs", tariffService.getAllDtoWithReq());
        model.addAttribute("optionsList", optionService.getAllDtoWithReqId());
        model.addAttribute("result", "Contract changes were successful!");
        return "editcontract";
    }
}
