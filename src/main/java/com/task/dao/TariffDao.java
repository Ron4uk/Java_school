package com.task.dao;

import com.task.dto.TariffDto;
import com.task.entity.Tariff;

import java.util.List;

public interface TariffDao extends GenericDao<Tariff> {
    List<Tariff> getAllWithout(Integer id);


}
