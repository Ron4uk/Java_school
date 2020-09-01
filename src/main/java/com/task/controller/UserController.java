package com.task.controller;

import com.task.dto.ContractDto;
import com.task.dto.DtoEntity;
import com.task.dto.OrderDto;
import com.task.entity.Contract;
import com.task.service.ClientService;
import com.task.service.ContractService;
import com.task.service.TariffService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@Getter
@Setter
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@SessionAttributes({"contractDto", "orderDto"})
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private ContractService contractService;
    private TariffService tariffService;

    @ModelAttribute("contractDto")
    public ContractDto getContractDto() {
        return new ContractDto();
    }

    @ModelAttribute("orderDto")
    public OrderDto getOrderDto() {
        return new OrderDto();
    }

    @GetMapping("/user")
    public String clientPage(@RequestParam(required = false) String id, Model model, HttpServletRequest request) {
        model.addAttribute("contractDto", contractService.getUserForAccountById(id,
                (ContractDto) request.getSession().getAttribute("contractDto")));

        return "user";
    }

    @GetMapping("/startauthclient")
    public String afterAuthenticationClient() {
        return "startauthclient";
    }
}
