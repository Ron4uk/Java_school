package com.task.service;

import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.dto.DtoEntity;
import com.task.entity.Contract;

import javax.security.auth.kerberos.KerberosTicket;

public interface ContractService {
    Contract findByPhone(String phone);
    DtoEntity convertToDto(Object obj, DtoEntity mapper);
    Object convertToEntity(Object obj, DtoEntity mapper);

    void create(com.task.dto.ContractDto contractDto, ClientDto clientDto, String tariffId, String[] optionsId);
    void check(String phone, ClientDto clientDto, com.task.dto.ContractDto contractDto);

    ClientDto findByIdDto(String id);

    ContractDto findByPhoneDto(String phone);
}
