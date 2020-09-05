package com.task.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task.entity.Contract;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OptionDto implements DtoEntity {
    private Integer id;
    private String name;
    private BigDecimal price;
    private BigDecimal connectionCost;
    private Boolean deleted;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionDto> requiredOptions= new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionDto> exclusionOptions= new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Set<TariffDto> tariffs= new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Integer> requirementsId= new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Integer> exclusionsId= new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Contract> contracts= new HashSet<>();



}
