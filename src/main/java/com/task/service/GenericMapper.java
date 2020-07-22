package com.task.service;

import com.task.dto.DtoEntity;
import org.modelmapper.ModelMapper;

public abstract class GenericMapper {
    public DtoEntity convertToDto(Object obj, DtoEntity mapper) {
        return new ModelMapper().map(obj, mapper.getClass());
    }

    public Object convertToEntity(Object obj, DtoEntity mapper) {
        return new ModelMapper().map(mapper, obj.getClass());
    }
}
