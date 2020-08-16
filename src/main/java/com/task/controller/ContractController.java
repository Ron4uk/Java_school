package com.task.controller;

import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.dto.OrderDto;
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

import javax.servlet.http.HttpServletRequest;

@Controller
@Getter
@Setter
@Log4j

@AllArgsConstructor(onConstructor = @__({@Autowired}))
@SessionAttributes({"contractDto", "clientDto", "orderDto"})
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

    @ModelAttribute("orderDto")
    public OrderDto getOrderDto() {
        return new OrderDto();
    }


    @GetMapping("/employee/newcontract")
    public String showNewContract() {

        return "newcontract";
    }


    @PostMapping("/employee/newcontract")
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

    @PostMapping("/employee/blockcontract")
    public String blockContract(HttpServletRequest request, Model model) {
        model.addAttribute("contract", contractService.block(request.getParameter("block")));
        model.addAttribute("result", "Client blocked!");
        return "employee";
    }


    @PostMapping("/employee/unblockcontract")
    public String unBlockContract(HttpServletRequest request, Model model) {
        model.addAttribute("contract", contractService.unblock(request.getParameter("unblock")));
        model.addAttribute("result", "Client unblocked!");
        return "employee";
    }


    @GetMapping("/employee/editcontract")
    public String showContract(HttpServletRequest request, Model model) {
        model.addAttribute("contractDto", contractService.findByIdDto(request.getParameter("id")));
        model.addAttribute("listTariffs", tariffService.getAllDtoWithReq());
        model.addAttribute("optionsList", optionService.getAllDtoWithReqIdWithDeleted());

        return "editcontract";
    }


    @PostMapping("/employee/editcontract")
    public String editContract(@ModelAttribute("contractDto") ContractDto contractDto, HttpServletRequest request,
                                   Model model) {
        model.addAttribute("result", contractService.update(contractDto,
                request.getParameterValues(contractDto.getTariffDto().getId().toString())));
        model.addAttribute("listTariffs", tariffService.getAllDtoWithReq());
        model.addAttribute("optionsList", optionService.getAllDtoWithReqIdWithDeleted());
        model.addAttribute("contractDto", contractService.findByIdDto(contractDto.getId().toString()));
        return "editcontract";
    }


    @GetMapping("/employee/getAllContracts")
    public String getAllContracts(@RequestParam(required = false) Integer id, Model model) {
        model.addAttribute("listContracts", contractService.getAllByPage(id));
        model.addAttribute("count", contractService.countContractsInBd());
        model.addAttribute("pagenumber", id==null?0:id);
        return "allcontracts";
    }

    @GetMapping("/user/usercontract")
    public String showUserContract(){
        return "usercontract";
    }

    @PostMapping("/user/blockcontractbyuser")
    public String blockContractByUser(@ModelAttribute("contractDto") ContractDto contractDto){
        contractService.blockByUser(contractDto);
        return "usercontract";
    }

    @PostMapping("/user/unblockcontractbyuser")
    public String unblockContractByUser(@ModelAttribute("contractDto") ContractDto contractDto){
        contractService.unblockByUser(contractDto);
        return "usercontract";
    }
}
