package com.task.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotExistClientException extends RuntimeException {
    private String message;
    private String path;

    public NotExistClientException(String message, String path) {
        this.message = message;
        this.path = path;
    }
}
