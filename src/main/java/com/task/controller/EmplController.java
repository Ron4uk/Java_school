package com.task.controller;

import com.task.dto.DtoEntity;
import com.task.dto.TariffDto;
import com.task.entity.Tariff;
import com.task.service.ClientServiceImpl;
import com.task.service.TariffServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.print.attribute.Attribute;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@SessionAttributes("tariffDto")
@Controller
public class EmplController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmplController.class);
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ClientServiceImpl clientService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TariffServiceImpl tariffService;

    @GetMapping("/employee")
    public String employeePage() {

        return "employee";
    }
    @GetMapping("/startauthempl")
    public String startEmployeePage() {

        return "startauthempl";
    }
    @GetMapping("/getAllClients")
    public String getAllClients (Model model){
        List<DtoEntity> clients = clientService.getAllDto();
        model.addAttribute("clientList",clients);
        return "employee";
    }
    @GetMapping("/tariffs")
    public String getAllTariffs (Model model){
        List<DtoEntity> tariffs = tariffService.getAllDto();
        model.addAttribute("tariffsList", tariffs);
        return "employee";
    }

    @ModelAttribute("tariffDto")
    public TariffDto getTariffDto() {
        return new TariffDto();
    }

    @GetMapping("/edittariff")
    public String editTariff(@RequestParam(value = "id", required = false) Integer id, Model model){
        LOGGER.info("[{}], GET edittariff  [{}]  tarif model = {}", LocalDateTime.now(), LOGGER.getName(), model.getAttribute("tariffDto"));
        if(id!=null) {
            Tariff tariff = tariffService.findById(id);
            DtoEntity tariffDto = tariffService.convertToDto(tariff, new TariffDto());
            model.addAttribute(tariffDto);
        }

        return "edittariff";
    }

    @PostMapping("/newTariff")
    public String newTariff(@ModelAttribute("tariffDto")  TariffDto tariffDto, Model model, SessionStatus sessionStatus){
        LOGGER.info("[{}], POST [{}]  tarif model = {}", LocalDateTime.now(), LOGGER.getName(), tariffDto);

        tariffService.merge((Tariff) tariffService.convertToEntity(new Tariff(), tariffDto));
        sessionStatus.setComplete();
        LOGGER.info("after setComplete model ={}", model.getAttribute("tariffDto") );
        model.addAttribute("tariffDto", new TariffDto());
        model.addAttribute("change", "changes were successful");
        return "edittariff";
    }
}
