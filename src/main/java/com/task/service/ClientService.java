package com.task.service;

import com.task.dto.DtoEntity;
import com.task.entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAll();
    List<DtoEntity> getAllDto();
    DtoEntity convertToDto(Object obj, DtoEntity mapper);
    Object convertToEntity(Object obj, DtoEntity mapper);
}
