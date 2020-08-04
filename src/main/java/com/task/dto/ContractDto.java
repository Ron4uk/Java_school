package com.task.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ContractDto implements DtoEntity {
    private Integer id;
    private String phone;
    private AuthorizationDto auth;
    private ClientDto clientDto;
    private TariffDto tariffDto;
    private Boolean blockByClient;
    private Boolean blockByOperator;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionDto> connectedOptions= new HashSet<>();


}
