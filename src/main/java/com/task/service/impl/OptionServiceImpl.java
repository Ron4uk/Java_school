package com.task.service.impl;

import com.task.dao.TariffDao;
import com.task.entity.Tariff;
import com.task.exception.WrongOptionException;
import com.task.dao.OptionDao;
import com.task.dto.DtoEntity;
import com.task.dto.OptionDto;
import com.task.entity.Option;
import com.task.service.GenericMapper;
import com.task.service.OptionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@Log4j
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@NoArgsConstructor
public class OptionServiceImpl extends GenericMapper implements OptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OptionServiceImpl.class);
    private OptionDao optionDao;
    private TariffDao tariffDao;
    private OptionValidationAndTools validationAndTools;


    @Transactional
    public List<Option> getAll() {
        return optionDao.getAll();
    }

    @Transactional
    public List<DtoEntity> getAllDto() {
        return optionDao.getAll().stream().map(e -> this.convertToDto(e, new OptionDto())).collect(Collectors.toList());
    }

    @Transactional
    public List<OptionDto> getAllDtoWithReqId() {
        List<OptionDto> result = optionDao.getAll().stream().map(e -> (OptionDto) this.convertToDto(e,
                new OptionDto())).collect(Collectors.toList());
        for (OptionDto optionDto : result) {
            optionDto.setExclusionsId(validationAndTools.setExclusions(optionDto));
            optionDto.setRequirementsId(validationAndTools.setRequirements(optionDto));

        }
        return result;
    }
    @Transactional
    @Override
    public List<OptionDto> getAllDtoWithReqIdWithDeleted() {
        List<OptionDto> result = optionDao.getAllWithDeleted().stream().map(e -> (OptionDto) this.convertToDto(e,
                new OptionDto())).collect(Collectors.toList());
        for (OptionDto optionDto : result) {
            optionDto.setExclusionsId(validationAndTools.setExclusions(optionDto));
            optionDto.setRequirementsId(validationAndTools.setRequirements(optionDto));

        }
        return result;
    }


    @Transactional
    public List<DtoEntity> getAllWithoutDto(Integer id) {
        return optionDao.getAllWithout(id).stream().map(e -> this.convertToDto(e,
                new OptionDto())).collect(Collectors.toList());
    }

    @Transactional
    public Option findById(Integer id) {
        return optionDao.findById(id);
    }

    @Transactional
    public DtoEntity findByIdDto(Integer id) {
        return convertToDto(optionDao.findById(id), new OptionDto());
    }


    @Transactional
    public String update(String[] requirement, String[] exclusion, OptionDto optionDto) {
        Option option = (Option) convertToEntity(new Option(), validationAndTools.
                globalCheck(requirement,  exclusion, optionDto));
        List<Tariff> tariffs = tariffDao.findAllTariffWithOption(option);
        tariffDao.update(tariffs, option);
        Option updatedOption = optionDao.update(option);
        optionDto.setId(updatedOption.getId());
        optionDto.setDeleted(false);
        return "Changes successful.";
    }

    @Transactional
    public String deleteById(Integer id, OptionDto optionDto) {
        if (optionDto.getDeleted() != true) {
            optionDto.setDeleted(true);
            Option option = optionDao.findById(id);
            option.setDeleted(true);
            optionDao.delete(option);
            return "Changes successful.";
        } else {
            return "Option was deleted";
        }


    }



}
