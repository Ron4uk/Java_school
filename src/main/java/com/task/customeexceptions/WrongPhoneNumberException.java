package com.task.customeexceptions;

import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WrongPhoneNumberException extends RuntimeException {
    private String message;
    private ClientDto clientDto;
    private ContractDto contractDto;


    public WrongPhoneNumberException(String message, ClientDto clientDto, ContractDto contractDto) {
        this.message = message;
        this.clientDto = clientDto;
        this.contractDto = contractDto;
    }
}
