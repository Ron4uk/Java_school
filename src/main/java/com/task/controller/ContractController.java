package com.task.controller;

import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.service.ContractService;
import com.task.service.OptionService;
import com.task.service.TariffService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
@Getter
@Setter
@Log4j
@RequestMapping("/employee")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
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
    public String addContract(@ModelAttribute("clientDto") ClientDto clientDto,
                              @ModelAttribute("contractDto") ContractDto contractDto,
                              HttpServletRequest request, Model model) {
        model.addAttribute("listTariffs", tariffService.getAllDtoWithReq());
        model.addAttribute("result", contractService.create(contractDto, clientDto,
                request.getParameter("chosentariff"),
                request.getParameterValues(request.getParameter("chosentariff"))));
        model.addAttribute("path", "/employee");
        return "newcontract";
    }


    @PostMapping("/blockcontract")
    public String blockContract(HttpServletRequest request, Model model) {
        model.addAttribute("contract", contractService.block(request.getParameter("block")));
        model.addAttribute("result", "Client blocked!");
        return "employee";
    }

    @PostMapping("/unblockcontract")
    public String unBlockContract(HttpServletRequest request, Model model) {
        model.addAttribute("contract", contractService.unblock(request.getParameter("unblock")));
        model.addAttribute("result", "Client unblocked!");
        return "employee";
    }

    @GetMapping("/editcontract")
    public String editGetContract(HttpServletRequest request, Model model) {
        model.addAttribute("contractDto", contractService.findByIdDto(request.getParameter("id")));
        model.addAttribute("listTariffs", tariffService.getAllDtoWithReq());
        model.addAttribute("optionsList", optionService.getAllDtoWithReqId());

        return "editcontract";
    }

    @PostMapping("/editcontract")
    public String editPostContract(@ModelAttribute("contractDto") ContractDto contractDto, HttpServletRequest request, Model model) {
        model.addAttribute("result", contractService.update(contractDto,
                request.getParameterValues(contractDto.getTariffDto().getId().toString())));
        model.addAttribute("listTariffs", tariffService.getAllDtoWithReq());
        model.addAttribute("optionsList", optionService.getAllDtoWithReqId());
        model.addAttribute("contractDto", contractService.findByIdDto(contractDto.getId().toString()));
        return "editcontract";
    }

    @GetMapping("getAllContracts")
    public String getAllContracts(Model model) {
        model.addAttribute("listContracts", contractService.getAll());
        return "allcontracts";
    }
}
