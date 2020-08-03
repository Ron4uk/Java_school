package com.task.controller.ExceptionHandler;

import com.task.controller.EmplController;
import com.task.customeexceptions.*;
import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.dto.OptionDto;
import com.task.entity.Option;
import com.task.service.OptionService;
import com.task.service.TariffService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.NestedServletException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmplController.class);

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TariffService tariffService;
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private OptionService optionService;

    @ExceptionHandler(value = WrongPassportException.class)
    public ModelAndView clientException(WrongPassportException exception){
        ModelAndView modelAndView = new ModelAndView("/newclient");
        modelAndView.addObject("clientDto", exception.getClientDto());
        modelAndView.addObject("result", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = WrongEmailException.class)
    public ModelAndView clientException(WrongEmailException exception){
        ModelAndView modelAndView = new ModelAndView("/newclient");
        modelAndView.addObject("clientDto", exception.getClientDto());
        modelAndView.addObject("result", exception.getMessage());
        return modelAndView;
    }
    @ExceptionHandler(value = WrongPhoneNumberException.class)
    public ModelAndView phoneException(WrongPhoneNumberException exception){
        ModelAndView modelAndView = new ModelAndView("/newcontract");
        modelAndView.addObject("clientDto", exception.getClientDto());
        modelAndView.addObject("contractDto", exception.getContractDto());
        modelAndView.addObject("listTariffs", tariffService.getAllDto());
        modelAndView.addObject("result", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = NotExistClientException.class)
    public ModelAndView clientException(NotExistClientException exception){
        ModelAndView modelAndView = new ModelAndView("/"+exception.getPath());
        modelAndView.addObject("result", exception.getMessage());
        modelAndView.addObject("contractDto", new ContractDto());
        return modelAndView;
    }

    @ExceptionHandler(value = WrongOptionException.class)
    public ModelAndView clientException(WrongOptionException exception){
        ModelAndView modelAndView = new ModelAndView("/editoption");
        modelAndView.addObject("result", exception.getMessage());
        modelAndView.addObject("optionDto", exception.getOptionDto());
        modelAndView.addObject("optionsList", optionService.getAllWithoutDto(exception.getOptionDto().getId()));

        return modelAndView;
    }

}
