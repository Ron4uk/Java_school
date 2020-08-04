package com.task.exception;

import com.task.dto.ClientDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WrongEmailException extends RuntimeException {
    private String message;
    private ClientDto clientDto;

    public WrongEmailException( String message, ClientDto clientDto) {
        this.message = message;
        this.clientDto = clientDto;
    }
}
