package com.task.controller;

import com.task.dto.ContractDto;
import com.task.dto.DtoEntity;
import com.task.dto.OptionDto;
import com.task.service.OptionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Getter
@Setter
@Log4j
@RequestMapping("/employee")
@AllArgsConstructor(onConstructor=@__({@Autowired}))
@SessionAttributes({"optionDto", "contractDto"})
public class OptionController {


    private OptionService optionService;

    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
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
            model.addAttribute("optionsList", optionService.getAllDto());
        }
        return "editoption";
    }

    @PostMapping("/updateOrDeleteOption")
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


}
