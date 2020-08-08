package com.task.converter;

import com.task.dto.OptionDto;
import com.task.service.OptionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component("optionDtoConverter")
@Getter
@Setter
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class OptionDtoConverter implements Converter<String, OptionDto> {
    private OptionService optionService;

    @Override
    public OptionDto convert(String id) {

        return (OptionDto) optionService.findByIdDto(Integer.parseInt(id));
    }
}
