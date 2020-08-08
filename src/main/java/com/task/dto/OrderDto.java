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
public class OrderDto {
    private TariffDto tariffDto;
    private Set<OptionDto> optionsFromNewTariff =new HashSet<>();
    private Set<OptionDto> optionsFromCurTariff =new HashSet<>();
    private Set<OptionDto> disableOptionsFromCurTariff =new HashSet<>();
}
