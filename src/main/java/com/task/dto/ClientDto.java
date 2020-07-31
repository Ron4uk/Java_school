package com.task.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClientDto implements DtoEntity {
    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private Date birthday;
    private String email;
    private String passport_series;
    private String passport_number;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ContractDto> contracts;

    public ClientDto() {
        this.contracts =new HashSet<>();
    }
}
