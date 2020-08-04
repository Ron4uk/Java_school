package com.task.exception;

import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WrongPhoneNumberException extends RuntimeException {
    private String message;
    private ClientDto clientDto;
    private ContractDto contractDto;



}
