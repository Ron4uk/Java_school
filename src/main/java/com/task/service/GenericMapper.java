package com.task.service;

import com.task.dto.DtoEntity;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericMapper {
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ModelMapper modelMapper;


    public DtoEntity convertToDto(Object obj, DtoEntity mapper) {

        return modelMapper.map(obj, mapper.getClass());
    }

    public Object convertToEntity(Object obj, DtoEntity mapper) {
        return modelMapper.map(mapper, obj.getClass());
    }
}
