package com.task.service;

import com.task.dto.DtoEntity;
import com.task.dto.OptionDto;
import com.task.dto.TariffDto;
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

    String merge(Tariff tariff, String[] optionId);

    TariffDto createRequirementsForEmbeddedOptions(DtoEntity tariffDto);
    DtoEntity convertToDto(Object obj, DtoEntity mapper);
    Object convertToEntity(Object obj, DtoEntity mapper);

    List<TariffDto> getAllDtoWithReq();
}
