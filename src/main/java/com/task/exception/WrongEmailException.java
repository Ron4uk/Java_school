package com.task.exception;

import com.task.dto.ClientDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WrongEmailException extends RuntimeException {
    private String message;
    private ClientDto clientDto;


}
