package com.task.service;

import com.task.controller.EmplController;
import com.task.dao.OptionDao;
import com.task.dto.DtoEntity;
import com.task.dto.OptionDto;
import com.task.entity.Option;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class OptionServiceImpl extends GenericMapper {
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private OptionDao optionDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(OptionServiceImpl.class);

    public List<Option> getAll() {
        return optionDao.getAll();
    }

    public List<DtoEntity> getAllDto() {
        return optionDao.getAll().stream().map(e -> this.convertToDto(e, new OptionDto())).collect(Collectors.toList());
    }

    public List<Option> getAllWithout(Integer id) {
        return optionDao.getAllWithout(id);
    }

    public List<DtoEntity> getAllWithoutDto(Integer id) {
        return optionDao.getAllWithout(id).stream().map(e -> this.convertToDto(e, new OptionDto())).collect(Collectors.toList());
    }

    public Option findById(Integer id) {
        return optionDao.findById(id);
    }

    public DtoEntity findByIdDto(Integer id) {
        return convertToDto(optionDao.findById(id), new OptionDto());
    }

    public String update(Option option) {
        String result = "change failed";
        try {
            optionDao.update(option);
            result = "changes successful";
        } catch (Exception e) {
            LOGGER.error("[{}],  update [{}]  exception = {}", LocalDateTime.now(), LOGGER.getName(), e);
            e.printStackTrace();

        }
        return result;
    }

    public OptionDto createOptionConstraint(String[] requirement, String[] exclusion, OptionDto optionDto) {
        if (requirement != null && requirement.length > 0) {
            Set<OptionDto> requiredOptions = new HashSet<>();
            for (String s1 : requirement) {

                OptionDto optionReqDto = (OptionDto) findByIdDto(Integer.parseInt(s1));
                requiredOptions.add(optionReqDto);

            }

            optionDto.setRequiredOptions(requiredOptions);

        }
        if (exclusion != null && exclusion.length > 0) {
            Set<OptionDto> exclusionOptions = new HashSet<>();
            for (String s2 : exclusion) {
                OptionDto optionExDto = (OptionDto) findByIdDto(Integer.parseInt(s2));
                exclusionOptions.add(optionExDto);
            }
            optionDto.setExclusionOptions(exclusionOptions);
        }
        return optionDto;
    }

    public String deleteById(Integer id) {
        String result = "change failed";
        try {
            optionDao.deleteById(id);
            result = "changes successful";
        } catch (Exception e) {
            LOGGER.error("[{}],  delete [{}]  exception = {}", LocalDateTime.now(), LOGGER.getName(), e);
            e.printStackTrace();

        }
        return result;
    }
}
