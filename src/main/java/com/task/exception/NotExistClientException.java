package com.task.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotExistClientException extends RuntimeException {
    private String message;
    private String path;


}
