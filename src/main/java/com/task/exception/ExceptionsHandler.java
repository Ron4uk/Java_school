package com.task.exception;

import com.task.dto.ContractDto;
import com.task.service.OptionService;
import com.task.service.TariffService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@ControllerAdvice
@Getter
@Setter(onMethod = @__({@Autowired}))
public class ExceptionsHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionsHandler.class);


    private TariffService tariffService;
    private OptionService optionService;

    @ExceptionHandler(value = WrongPassportException.class)
    public ModelAndView clientException(WrongPassportException exception) {
        ModelAndView modelAndView = new ModelAndView("/newclient");
        modelAndView.addObject("clientDto", exception.getClientDto());
        modelAndView.addObject("result", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = WrongEmailException.class)
    public ModelAndView clientException(WrongEmailException exception) {
        ModelAndView modelAndView = new ModelAndView("/newclient");
        modelAndView.addObject("clientDto", exception.getClientDto());
        modelAndView.addObject("result", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = WrongPhoneNumberException.class)
    public ModelAndView phoneException(WrongPhoneNumberException exception) {
        ModelAndView modelAndView = new ModelAndView("/newcontract");
        modelAndView.addObject("clientDto", exception.getClientDto());
        modelAndView.addObject("contractDto", exception.getContractDto());
        modelAndView.addObject("listTariffs", tariffService.getAllDtoWithReq());
        modelAndView.addObject("result", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = NotExistClientException.class)
    public ModelAndView clientException(NotExistClientException exception) {
        ModelAndView modelAndView = new ModelAndView(exception.getPath());
        modelAndView.addObject("result", exception.getMessage());
        modelAndView.addObject("contractDto", new ContractDto());
        return modelAndView;
    }

    @ExceptionHandler(value = WrongOptionException.class)
    public ModelAndView clientException(WrongOptionException exception) {
        ModelAndView modelAndView = new ModelAndView("/editoption");
        modelAndView.addObject("result", exception.getMessage());
        modelAndView.addObject("optionDto", exception.getOptionDto());
        modelAndView.addObject("optionsList", exception.getOptionDto().getId() == null ?
                optionService.getAllDto() : optionService.getAllWithoutDto(exception.getOptionDto().getId()));

        return modelAndView;
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String page404Exception() {
        return "/404";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String notFoundHandler(Exception exception) {

        StringWriter errors = new StringWriter();
        exception.printStackTrace(new PrintWriter(errors));
        LOGGER.error("[{} ERROR: {}]",LocalDateTime.now(), errors.toString());
        return "/500";
    }


}
