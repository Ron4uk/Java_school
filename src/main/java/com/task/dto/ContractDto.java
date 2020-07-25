package com.task.dto;

import com.task.entity.Authorization;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContractDto implements DtoEntity {
    private Integer id;
    private String phone;
    private Authorization auth;
}
