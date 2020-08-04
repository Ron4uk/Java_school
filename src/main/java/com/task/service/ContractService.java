package com.task.service;

import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.dto.DtoEntity;
import com.task.entity.Contract;

import java.util.List;

public interface ContractService {
    Contract findByPhone(String phone);
    DtoEntity convertToDto(Object obj, DtoEntity mapper);
    Object convertToEntity(Object obj, DtoEntity mapper);

    void create(com.task.dto.ContractDto contractDto, ClientDto clientDto, String tariffId, String[] optionsId);
    void check(String phone, ClientDto clientDto, com.task.dto.ContractDto contractDto);

    ContractDto findByIdDto(String id);

    ContractDto findByPhoneDto(String phone, String path);

    ContractDto block(String id);

    ContractDto unblock(String id);

    ContractDto update(ContractDto contractDto, String[] connectedOptions);

    List<ContractDto> getAll();
}
