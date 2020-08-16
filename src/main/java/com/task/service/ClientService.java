package com.task.service;

import com.task.dto.ClientDto;
import com.task.dto.DtoEntity;
import com.task.entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAll();
    List<DtoEntity> getAllDto();
    DtoEntity convertToDto(Object obj, DtoEntity mapper);
    Object convertToEntity(Object obj, DtoEntity mapper);
    DtoEntity update(com.task.dto.ClientDto clientDto);

    void check(com.task.dto.ClientDto clientDto);

    ClientDto findByIdDto(String id);

    List<ClientDto> getAllDtoByPage(Integer id);

    Long countContractsInBd();
}
