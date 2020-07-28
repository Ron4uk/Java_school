package com.task.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode

public class TariffDto implements DtoEntity {
    private Integer id;
    private String tariff;
    private Double price;
    private boolean deprecated;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionDto> options;

    public TariffDto() {
        this.options = new HashSet<>();
    }


}
