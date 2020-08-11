package com.task.dto;

import com.task.entity.Contract;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TariffDto implements DtoEntity {
    private Integer id;
    private String tariff;
    private Double price;
    private Boolean deleted;
     @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ContractDto> contracts= new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionDto> options= new HashSet<>();



}
