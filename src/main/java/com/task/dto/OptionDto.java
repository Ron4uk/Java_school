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
public class OptionDto implements DtoEntity {
    private Integer id;
    private String name;
    private Integer price;
    private Integer connectionCost;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionDto> requiredOptions= new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionDto> exclusionOptions= new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TariffDto> tariffs= new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Integer> requirementsId= new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Integer> exclusionsId= new HashSet<>();




}
