package com.task.exception;

import com.task.dto.OptionDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WrongOptionException extends RuntimeException {
    private String message;
    private OptionDto optionDto;

    public WrongOptionException(String message, OptionDto optionDto) {
        this.message = message;
        this.optionDto = optionDto;
    }
}
