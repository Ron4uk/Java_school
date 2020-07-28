package com.task.service;

import com.task.dto.DtoEntity;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericMapper {
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericMapper.class);
    public DtoEntity convertToDto(Object obj, DtoEntity mapper) {

        return modelMapper.map(obj, mapper.getClass());
    }

    public Object convertToEntity(Object obj, DtoEntity mapper) {
        return modelMapper.map(mapper, obj.getClass());
    }
}
