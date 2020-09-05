package com.task.service;

import com.task.dto.DtoEntity;
import com.task.dto.TariffDto;
import com.task.entity.Tariff;

import java.util.List;

public interface TariffService {

    List<Tariff> getAll();

    List<DtoEntity> getAllDto();

    Tariff findById(Integer id);

    String merge(TariffDto tariffDto, String[] optionId);

    TariffDto createRequirementsForEmbeddedOptions(DtoEntity tariffDto);
    DtoEntity convertToDto(Object obj, DtoEntity mapper);
    Object convertToEntity(Object obj, DtoEntity mapper);

    List<TariffDto> getAllDtoWithReq();

    TariffDto findByIdDto(Integer id);

    List<TariffDto> getAllWithoutDto(Integer id);

    TariffDto remove(TariffDto tariffDto, Integer id);


    void sendTariffsToQueue();
}
