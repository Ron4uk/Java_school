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
    private Set<OptionDto> requiredOptions;
    private Set<OptionDto> exclusionOptions;

    public OptionDto() {
        this.requiredOptions = new HashSet<>();
        this.exclusionOptions =  new HashSet<>();
    }
}
