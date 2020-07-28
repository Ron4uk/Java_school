package com.task.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
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
    private Set<OptionDto> requiredOptions;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionDto> exclusionOptions;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TariffDto> tariffs;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Integer> requirementsId;

    public OptionDto() {
        this.requiredOptions = new HashSet<>();
        this.exclusionOptions =  new HashSet<>();
        this.tariffs=new HashSet<>();
        this.requirementsId = new HashSet<>();
    }


}
