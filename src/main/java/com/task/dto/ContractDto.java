package com.task.dto;

import com.task.entity.Authorization;
import com.task.entity.Client;
import com.task.entity.Option;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString

public class ContractDto implements DtoEntity {
    private Integer id;
    private String phone;
    private Authorization auth;
    private ClientDto clientDto;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionDto> connectedOptions;

    public ContractDto() {
        this.connectedOptions = new HashSet<>();
    }
}
