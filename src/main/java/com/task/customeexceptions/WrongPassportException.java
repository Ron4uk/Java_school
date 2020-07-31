package com.task.customeexceptions;

import com.task.dto.ClientDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WrongPassportException extends RuntimeException {
    private String message;
    private ClientDto clientDto;

    public WrongPassportException( String message, ClientDto clientDto) {
        this.message = message;
        this.clientDto = clientDto;
    }
}
