package com.task.controller;

import com.task.dto.DtoEntity;
import com.task.service.ClientService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@Getter
@Setter(onMethod = @__({@Autowired}))
@RequiredArgsConstructor()
public class UserController {

    private ClientService clientService;

    //TODO TEST with DB
    @GetMapping("/client")
    public String clientPage (Map<String, Object> model) {
        List<DtoEntity> clients = clientService.getAllDto();
        model.put("message", clients);
        return "client";
    }
}
