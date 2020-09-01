package com.task.controller;

import com.task.dto.ContractDto;
import com.task.dto.DtoEntity;
import com.task.dto.OrderDto;
import com.task.dto.TariffDto;
import com.task.entity.Tariff;
import com.task.service.ContractService;
import com.task.service.OptionService;
import com.task.service.TariffService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
@Getter
@Setter
@Log4j
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@SessionAttributes({"tariffDto", "contractDto", "orderDto"})
public class TariffController {

    private TariffService tariffService;
    private OptionService optionService;
    private ContractService contractService;

    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
    }


    @ModelAttribute("tariffDto")
    public TariffDto getTariffDto() {
        return new TariffDto();
    }


    @GetMapping("/employee/tariffs")
    public String getAllTariffs(Model model) {
        List<DtoEntity> tariffs = tariffService.getAllDto();
        model.addAttribute("tariffsList", tariffs);
        return "employee";
    }


    @GetMapping("/employee/edittariff")
    public String editTariff(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute(tariffService.findByIdDto(id));
        }
        model.addAttribute("optionsList", optionService.getAllDtoWithReqId());
        return "edittariff";
    }

    @PostMapping("/employee/newTariff")
    public String newTariff(@ModelAttribute("tariffDto") TariffDto tariffDto, HttpServletRequest request, Model model, HttpSession session) {

        log.info("session before "+session.toString());
        String result = tariffService.merge(tariffDto, request.getParameterValues("opt"));
        model.addAttribute("result", result);
        model.addAttribute("optionsList", optionService.getAllDtoWithReqId());
        log.info("session after  "+session.toString());
        return "edittariff";
    }

    @GetMapping("/employee/deletetariff")
    public String deleteTariffGet(@RequestParam Integer id, Model model) {
        model.addAttribute("tariffDto", tariffService.findByIdDto(id));
        model.addAttribute("tariffsList", tariffService.getAllWithoutDto(id));
        return "deletetariff";
    }

    @PostMapping("/employee/deletetariff")
    public String deleteTariffPost(@ModelAttribute("tariffDto") TariffDto tariffDto, @RequestParam(value = "newtariff") Integer id, Model model) {
        tariffService.remove(tariffDto, id);
        model.addAttribute("tariffDto", tariffService.findByIdDto(id));
        model.addAttribute("tariffsList", tariffService.getAllWithoutDto(id));
        model.addAttribute("result", true);
        return "deletetariff";
    }

    @GetMapping("/user/userchoosenewtariff")
    public String chooseNewTariff(@ModelAttribute("contractDto") ContractDto contractDto, @ModelAttribute("orderDto") OrderDto orderDto, Model model){
        model.addAttribute("listTariffs", tariffService.getAllWithoutDto(contractDto.getTariffDto().getId()));
        return  contractService.checkContract(contractDto, orderDto)? "userchoosenewtariff":"user";
    }

    @PostMapping("/user/addtarifftoorder")
    public String addTariffToOrder(@ModelAttribute("orderDto") OrderDto orderDto, Model model){
        tariffService.createRequirementsForEmbeddedOptions(orderDto.getTariffDto());
        model.addAttribute("listOptions", orderDto.getTariffDto().getOptions());
        return "userchoosenewoption";
    }

    @GetMapping("/tariff")
    @ResponseBody
    public List<TariffDto> getTariffs(HttpSession httpSession) {
        return tariffService.getAllDtoWithReq();
    }
}
