package com.task.controller;

import com.task.dto.DtoEntity;
import com.task.dto.OptionDto;
import com.task.dto.TariffDto;
import com.task.entity.Option;
import com.task.entity.Tariff;
import com.task.service.ClientService;
import com.task.service.OptionService;
import com.task.service.TariffService;
import com.task.service.implementation.ClientServiceImpl;
import com.task.service.implementation.OptionServiceImpl;
import com.task.service.implementation.TariffServiceImpl;
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

@SessionAttributes({"tariffDto", "optionDto"})

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

    @GetMapping("/employee")
    public String employeePage(SessionStatus sessionStatus, Model model) {
        sessionStatus.setComplete();
        model.addAttribute("optionDto", new OptionDto());
        model.addAttribute("tariffDto", new TariffDto());
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
        LOGGER.info("[{}],  GET tariff  enter", LocalDateTime.now());
        if (id != null) {
            LOGGER.info("[{}],  id={}", LocalDateTime.now(), id);
            Tariff tariff = tariffService.findById(id);
            LOGGER.info("[{}],  tariff={}", LocalDateTime.now(), tariff);
            TariffDto tariffDto = tariffService.createRequirementsForEmbeddedOptions(tariffService.convertToDto(tariff, new TariffDto()));
            model.addAttribute(tariffDto);
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
        LOGGER.info("[{}], POST [{}]  result = {}", LocalDateTime.now(), LOGGER.getName(), result);
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
        if (request.getParameter("delete") != null) {
            String result = optionService.deleteById(optionDto.getId());
            model.addAttribute("result", result);
            LOGGER.info("[{}], POST saveOrUpdateOption DELETE [{}]  optionDto = {}", LocalDateTime.now(), LOGGER.getName(), optionDto);
        } else {
            String result = optionService.update(request.getParameterValues("requirement"), request.getParameterValues("exclusion"), optionDto);
            model.addAttribute("result", result);
            LOGGER.info("[{}], POST saveOrUpdateOption [{}]  optionDto = {}, result={}", LocalDateTime.now(), LOGGER.getName(), optionDto, result);
        }
        List<DtoEntity> options = optionService.getAllDto();
        model.addAttribute("optionsList", options);
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
}
