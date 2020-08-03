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
     @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ContractDto> contracts;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionDto> options;

    public TariffDto() {

        this.options = new HashSet<>();
        this.contracts= new HashSet<>();
    }


}
