package com.task.controller;

import com.task.dto.ContractDto;
import com.task.dto.DtoEntity;
import com.task.dto.TariffDto;
import com.task.entity.Tariff;
import com.task.service.OptionService;
import com.task.service.TariffService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Getter
@Setter(onMethod = @__({@Autowired}))
@RequiredArgsConstructor()
@SessionAttributes({"tariffDto", "contractDto"})
public class TariffController {

    private TariffService tariffService;
    private OptionService optionService;

    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
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
        model.addAttribute("result", true);
        return "deletetariff";
    }
}
