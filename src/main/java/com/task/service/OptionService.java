package com.task.service;

import com.task.dto.DtoEntity;
import com.task.dto.OptionDto;
import com.task.entity.Option;

import java.util.List;

public interface OptionService {
    Option findById(Integer id);
    List<Option> getAll();
    List<DtoEntity> getAllDto();
    List<OptionDto> getAllDtoWithReqId();
    List<DtoEntity> getAllWithoutDto(Integer id);
    DtoEntity findByIdDto(Integer id);
    String deleteById(Integer id, OptionDto optionDto);
    DtoEntity convertToDto(Object obj, DtoEntity mapper);
    Object convertToEntity(Object obj, DtoEntity mapper);
    String update(String[] requirement, String[] exclusion, OptionDto optionDto);
    List<OptionDto> getAllDtoWithReqIdWithDeleted();
}
