package com.task.service;

import com.task.dto.*;
import com.task.entity.Contract;

import java.util.List;
import java.util.Set;

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
    Boolean checkContract(ContractDto contractDto, OrderDto orderDto);


    void checkOrder(OrderDto orderDto, String path);

    void deleteTariffFromOrder(OrderDto orderDto);

    void deleteOptionFromOrder(Set<OptionDto> optionsFromNewTariff, Integer id);

    String confirmContractUserChanges(ContractDto contractDto, OrderDto orderDto);

    void blockByUser(ContractDto contractDto);

    void unblockByUser(ContractDto contractDto);
}
