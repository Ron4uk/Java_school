package com.task.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class ClientDto implements DtoEntity {
    private Integer id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String email;
    private String passport_series;
    private String passport_number;
}
