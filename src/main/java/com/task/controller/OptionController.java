package com.task.controller;

import com.task.dto.ContractDto;
import com.task.dto.DtoEntity;
import com.task.dto.OptionDto;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Getter
@Setter
@Log4j
@AllArgsConstructor(onConstructor=@__({@Autowired}))
@SessionAttributes({"optionDto", "contractDto", "orderDto"})
public class OptionController {

    private ContractService contractService;
    private TariffService tariffService;
    private OptionService optionService;

    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
    }

    @ModelAttribute("optionDto")
    public OptionDto getOptionDto() {
        return new OptionDto();
    }

    @GetMapping("/employee/options")
    public String getAllOptions(Model model) {
        List<DtoEntity> options = optionService.getAllDto();
        model.addAttribute("optionsList", options);
        return "employee";
    }

    @GetMapping("/employee/editoption")
    public String editOption(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("optionDto", optionService.findByIdDto(id));
            model.addAttribute("optionsList", optionService.getAllWithoutDto(id));
        } else {
            model.addAttribute("optionsList", optionService.getAllDto());
        }
        return "editoption";
    }

    @PostMapping("/employee/updateOrDeleteOption")
    public String updateOrDeleteOption(@ModelAttribute("optionDto") OptionDto optionDto, HttpServletRequest request,
                                       Model model) {
        if (request.getParameter("delete") != null) {
            model.addAttribute("result", optionService.deleteById(optionDto.getId(), optionDto));
        } else {
            model.addAttribute("result", optionService.update(request.getParameterValues("requirement"),
                    request.getParameterValues("exclusion"), optionDto));
        }
        model.addAttribute("optionsList", optionService.getAllWithoutDto(optionDto.getId()));
        return "editoption";
    }

    @GetMapping("/user/userchoosenewoption")
    public String chooseNewOption(@ModelAttribute("contractDto") ContractDto contractDto, @ModelAttribute("orderDto")
            OrderDto orderDto) {
        return contractService.checkContract(contractDto, orderDto) ? "userchoosenewoption" : "user";
    }

    @PostMapping("/user/addoptiontotariff")
    public String addOptionToTariff(@ModelAttribute("orderDto") OrderDto orderDto) {
        return "user";
    }

    @GetMapping("/user/usermanageoption")
    public String manageOption() {
        return "usermanageoption";
    }

    @PostMapping("/user/connectedoptionbyuser")
    public String connectedNewOptionByUser(@ModelAttribute("contractDto") ContractDto contractDto,
                                           @RequestParam(name = "optid") Integer id, ModelAndView model){
        model.addObject("result", contractService.addConnectedOption(contractDto, id));
        return "usermanageoption";
    }

    @PostMapping("/user/disconnectoptionbyuser")
    public String disconnectOptionByUser(@ModelAttribute("contractDto") ContractDto contractDto,
                                         @RequestParam(name = "optid") Integer id, Model model){
        model.addAttribute("result", contractService.disconnectOption(contractDto, id));
        return "usermanageoption";
    }

}
