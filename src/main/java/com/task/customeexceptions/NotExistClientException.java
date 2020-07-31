package com.task.customeexceptions;

import com.task.dto.ClientDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotExistClientException extends RuntimeException {
    private String message;

    public NotExistClientException(String message) {
        this.message = message;
    }
}
