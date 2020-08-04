package com.task.converter;

import com.task.dto.TariffDto;
import com.task.service.TariffService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("tariffDtoConverter")
@Getter
@Setter
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class TariffDtoConverter implements Converter<String, TariffDto> {

    private TariffService tariffService;

    @Override
    public TariffDto convert(String id) {
        return (TariffDto)  tariffService.convertToDto( tariffService.findById(Integer.parseInt(id)), new TariffDto());
    }
}
