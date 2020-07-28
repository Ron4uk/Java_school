package com.task.service;

import com.task.dto.DtoEntity;
import com.task.entity.Contract;

public interface ContractService {
    Contract findByPhone(String phone);
    DtoEntity convertToDto(Object obj, DtoEntity mapper);
    Object convertToEntity(Object obj, DtoEntity mapper);
}
