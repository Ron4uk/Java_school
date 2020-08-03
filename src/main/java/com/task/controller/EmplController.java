package com.task.controller;

import com.task.dto.*;
import com.task.entity.Tariff;
import com.task.service.ClientService;
import com.task.service.ContractService;
import com.task.service.OptionService;
import com.task.service.TariffService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@SessionAttributes({"tariffDto", "optionDto", "clientDto", "contractDto"})
@Controller
public class EmplController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmplController.class);
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ClientService clientService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TariffService tariffService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private OptionService optionService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ContractService contractService;

    @GetMapping("/employee")
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

    @GetMapping("/getAllClients")
    public String getAllClients(Model model) {
        model.addAttribute("clientList", clientService.getAllDto());
        return "employee";
    }

    @GetMapping("/tariffs")
    public String getAllTariffs(Model model) {
        List<DtoEntity> tariffs = tariffService.getAllDto();
        model.addAttribute("tariffsList", tariffs);
        return "employee";
    }

    @ModelAttribute("tariffDto")
    public TariffDto getTariffDto() {
        return new TariffDto();
    }

    @GetMapping("/edittariff")
    public String editTariff(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id != null) {
            //TODO make it easy (check it)
//            Tariff tariff = tariffService.findByIdDto(id);
//            TariffDto tariffDto = tariffService.createRequirementsForEmbeddedOptions(tariffService.convertToDto(tariff, new TariffDto()));
//            LOGGER.info("[{}], editTariff tariffDto options={} ", LocalDateTime.now(),tariffDto.getOptions());
            model.addAttribute(tariffService.findByIdDto(id));

        }
        model.addAttribute("optionsList", optionService.getAllDtoWithReqId());
        return "edittariff";
    }

    @PostMapping("/newTariff")
    public String newTariff(@ModelAttribute("tariffDto") TariffDto tariffDto, HttpServletRequest request, Model model, SessionStatus sessionStatus) {

        String result = tariffService.merge((Tariff) tariffService.convertToEntity(new Tariff(), tariffDto), request.getParameterValues("opt"));
        sessionStatus.setComplete();
        model.addAttribute("tariffDto", new TariffDto());
        model.addAttribute("result", result);
        model.addAttribute("optionsList", optionService.getAllDto());

        return "edittariff";
    }

    @GetMapping("/deletetariff")
    public String deleteTariffGet(@RequestParam Integer id, Model model){
        model.addAttribute("tariffDto", tariffService.findByIdDto(id));
        model.addAttribute("tariffsList", tariffService.getAllWithoutDto(id));
        return  "deletetariff";
    }

    @PostMapping("/deletetariff")
    public String deleteTariffPost(@ModelAttribute("tariffDto") TariffDto tariffDto, @RequestParam(value = "newtariff") Integer id, Model model){
        tariffService.remove(tariffDto, id);
        model.addAttribute("tariffDto", tariffService.findByIdDto(id));
        model.addAttribute("tariffsList", tariffService.getAllWithoutDto(id));
        return "deletetariff";
    }


    @GetMapping("/options")
    public String getAllOptions(Model model) {
        List<DtoEntity> options = optionService.getAllDto();
        model.addAttribute("optionsList", options);
        return "employee";
    }


    @ModelAttribute("optionDto")
    public OptionDto getOptionDto() {
        return new OptionDto();
    }


    @GetMapping("/editoption")
    public String editOption(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute(optionService.findByIdDto(id));
            model.addAttribute("optionsList", optionService.getAllWithoutDto(id));
        } else {
            List<DtoEntity> options = optionService.getAllDto();
            LOGGER.info("[{}], Get editoption [{}]  options List = {}", LocalDateTime.now(), LOGGER.getName(), options);
            model.addAttribute("optionsList", options);
        }
        LOGGER.info("[{}], Get editoption after cycle [{}]  id = {}", LocalDateTime.now(), LOGGER.getName(), id);
        return "editoption";
    }

    @PostMapping("/updateOrDeleteOption")
    public String updateOrDeleteOption(@ModelAttribute("optionDto") OptionDto optionDto, HttpServletRequest request, Model model, SessionStatus sessionStatus) {
        if (request.getParameter("delete") != null) {
            model.addAttribute("result", optionService.deleteById(optionDto.getId()));
        } else {
            model.addAttribute("result", optionService.update(request.getParameterValues("requirement"), request.getParameterValues("exclusion"), optionDto));
        }
        model.addAttribute("optionsList", optionService.getAllDto());
        sessionStatus.setComplete();
        model.addAttribute("optionDto", new OptionDto());
        return "editoption";
    }

    @PostMapping("/deleteoption")
    public String deleteOption(@RequestParam(value = "id") Integer id, Model model) {
        String result = optionService.deleteById(id);
        model.addAttribute("result", result);
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
        return "/newcontract";
    }

    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
    }

    @GetMapping("/newcontract")
    public String newContract() {

        return "newcontract";
    }

    @PostMapping("/newcontract")
    public String addContract(@ModelAttribute("clientDto") ClientDto clientDto, @ModelAttribute("contractDto") ContractDto contractDto, HttpServletRequest request, Model model) {

        contractService.create(contractDto, clientDto, request.getParameter("chosentariff"), request.getParameterValues(request.getParameter("chosentariff")));
       return "employee";
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
        return "newcontract";
    }

    @PostMapping("/findClientOnMainPage")
    public String findClientOnMainPage(@ModelAttribute("contractDto") ContractDto contractDto, Model model){
        if(contractDto.getPhone()!=null) model.addAttribute("contract", contractService.findByPhoneDto(contractDto.getPhone(), "employee"));
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
        return "employee";
    }
}
