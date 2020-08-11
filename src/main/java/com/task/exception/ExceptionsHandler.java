package com.task.exception;

import com.task.controller.EmplController;
import com.task.exception.*;
import com.task.dto.ContractDto;
import com.task.service.OptionService;
import com.task.service.TariffService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Getter
@Setter(onMethod = @__({@Autowired}))
public class ExceptionsHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmplController.class);


    private TariffService tariffService;
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
        modelAndView.addObject("listTariffs", tariffService.getAllDtoWithReq());
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
        modelAndView.addObject("optionsList", exception.getOptionDto().getId()==null? optionService.getAllDto():optionService.getAllWithoutDto(exception.getOptionDto().getId()));

        return modelAndView;
    }

}
