package com.task.service;

import com.task.dto.*;
import com.task.entity.Option;
import com.task.entity.Tariff;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface TariffService {

    List<Tariff> getAll();

    List<DtoEntity> getAllDto();

    Tariff create(Tariff tariff);

    Tariff findById(Integer id);

    String merge(TariffDto tariffDto, String[] optionId);

    TariffDto createRequirementsForEmbeddedOptions(DtoEntity tariffDto);
    DtoEntity convertToDto(Object obj, DtoEntity mapper);
    Object convertToEntity(Object obj, DtoEntity mapper);

    List<TariffDto> getAllDtoWithReq();

    TariffDto findByIdDto(Integer id);

    List<TariffDto> getAllWithoutDto(Integer id);

    TariffDto remove(TariffDto tariffDto, Integer id);


}
