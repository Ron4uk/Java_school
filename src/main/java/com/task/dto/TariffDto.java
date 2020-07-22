package com.task.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TariffDto implements DtoEntity {
    private int id;
    private String tariff;
    private Double price;
    private boolean deprecated;
}
