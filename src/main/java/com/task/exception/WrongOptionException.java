package com.task.exception;

import com.task.dto.OptionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WrongOptionException extends RuntimeException {
    private String message;
    private OptionDto optionDto;


}
