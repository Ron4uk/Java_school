package com.task.controller;

import com.task.dto.DtoEntity;
import com.task.dto.OptionDto;
import com.task.dto.TariffDto;
import com.task.entity.Option;
import com.task.entity.Tariff;
import com.task.service.ClientServiceImpl;
import com.task.service.OptionServiceImpl;
import com.task.service.TariffServiceImpl;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SessionAttributes({"tariffDto", "optionDto"})

@Controller
public class EmplController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmplController.class);
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ClientServiceImpl clientService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TariffServiceImpl tariffService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private OptionServiceImpl optionService;

    @GetMapping("/employee")
    public String employeePage() {

        return "employee";
    }

    @GetMapping("/startauthempl")
    public String startEmployeePage() {

        return "startauthempl";
    }

    @GetMapping("/getAllClients")
    public String getAllClients(Model model) {
        List<DtoEntity> clients = clientService.getAllDto();
        model.addAttribute("clientList", clients);
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
        LOGGER.info("[{}], GET edittariff  [{}]  tarif model = {}", LocalDateTime.now(), LOGGER.getName(), model.getAttribute("tariffDto"));
        if (id != null) {
            Tariff tariff = tariffService.findById(id);
            DtoEntity tariffDto = tariffService.convertToDto(tariff, new TariffDto());
            model.addAttribute(tariffDto);
        }

        return "edittariff";
    }

    @PostMapping("/newTariff")
    public String newTariff(@ModelAttribute("tariffDto") TariffDto tariffDto, Model model, SessionStatus sessionStatus) {
        LOGGER.info("[{}], POST [{}]  tarif model = {}", LocalDateTime.now(), LOGGER.getName(), tariffDto);

        tariffService.merge((Tariff) tariffService.convertToEntity(new Tariff(), tariffDto));
        sessionStatus.setComplete();

        model.addAttribute("tariffDto", new TariffDto());
        model.addAttribute("change", "changes  successful");
        return "edittariff";
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
            DtoEntity optionDto = optionService.findByIdDto(id);
            model.addAttribute(optionDto);
            List<DtoEntity> options = optionService.getAllWithoutDto(id);
            model.addAttribute("optionsList", options);
        } else {
            List<DtoEntity> options = optionService.getAllDto();
            LOGGER.info("[{}], Get editoption [{}]  options List = {}", LocalDateTime.now(), LOGGER.getName(), options);
            model.addAttribute("optionsList", options);
        }
        LOGGER.info("[{}], Get editoption after cycle [{}]  id = {}", LocalDateTime.now(), LOGGER.getName(), id);
        return "editoption";
    }

    @PostMapping("/saveOrUpdateOption")
    public String saveOrUpdateOption(@ModelAttribute("optionDto") OptionDto optionDto, HttpServletRequest request, Model model, SessionStatus sessionStatus) {
        optionDto=optionService.createOptionConstraint(request.getParameterValues("requirement"), request.getParameterValues("exclusion"), optionDto);
        LOGGER.info("[{}], POST saveOrUpdateOption [{}]  optionDto = {}", LocalDateTime.now(), LOGGER.getName(), optionDto);

        try {
            optionService.update((Option) optionService.convertToEntity(new Option(), optionDto));
            model.addAttribute("change", "changes  successful");
        } catch (Exception e) {
            model.addAttribute("error", "change failed");
            LOGGER.error("[{}], POST saveOrUpdateOption class [{}]  error = {}", LocalDateTime.now(), LOGGER.getName(), e.printStackTrace(););
        }
        sessionStatus.setComplete();
        model.addAttribute("optionDto", new OptionDto());
        List<DtoEntity> options = optionService.getAllDto();
        model.addAttribute("optionsList", options);
        return  "editoption";
    }

}
