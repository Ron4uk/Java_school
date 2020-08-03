package com.task.dao;

import com.task.dto.ClientDto;
import com.task.entity.Contract;
import com.task.entity.Tariff;

import java.util.List;

public interface ContractDao extends GenericDao<Contract> {
    Contract findByPhone(String phone);

    void check(String phone, ClientDto clientDto, com.task.dto.ContractDto contractDto);

    List<Contract> getAllWithOldTariff(Tariff tariff);
}
