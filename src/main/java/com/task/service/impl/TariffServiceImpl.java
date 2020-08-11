package com.task.service.impl;

import com.task.dao.ContractDao;
import com.task.dao.OptionDao;
import com.task.dao.TariffDao;
import com.task.dto.*;
import com.task.entity.Contract;
import com.task.entity.Option;
import com.task.entity.Tariff;
import com.task.service.ContractService;
import com.task.service.GenericMapper;
import com.task.service.OptionService;
import com.task.service.TariffService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
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
@Getter
@Setter
@Log4j
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TariffServiceImpl extends GenericMapper implements TariffService {

    private TariffDao tariffDao;
    private OptionDao optionDao;
    private OptionService optionService;
    private ContractDao contractDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(TariffServiceImpl.class);

    @Transactional
    public List<Tariff> getAll() {
        return tariffDao.getAll();
    }
    @Transactional
    public List<DtoEntity> getAllDto() {

        return tariffDao.getAll().stream().map(e -> this.convertToDto(e, new TariffDto())).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public List<TariffDto> getAllDtoWithReq() {
        List<TariffDto> tariffDtoList = tariffDao.getAll().stream().map(e -> (TariffDto) this.convertToDto(e, new TariffDto())).collect(Collectors.toList());
        tariffDtoList.forEach(e -> createRequirementsForEmbeddedOptions(e));
        return tariffDtoList;
    }
    @Transactional
    @Override
    public List<TariffDto> getAllWithoutDto(Integer id) {
        List<TariffDto> tariffDtoList=tariffDao.getAllWithout(id).stream().map(e -> (TariffDto) this.convertToDto(e, new TariffDto())).collect(Collectors.toList());
        tariffDtoList.forEach(e -> createRequirementsForEmbeddedOptions(e));
        return tariffDtoList;
    }
    @Transactional
    public Tariff create(Tariff tariff) {
        return tariffDao.create(tariff);
    }
    @Transactional
    public Tariff findById(Integer id) {
        return tariffDao.findById(id);
    }
    @Transactional
    @Override
    public TariffDto findByIdDto(Integer id) {
        Tariff tariff = tariffDao.findById(id);
        TariffDto tariffDto = (TariffDto) createRequirementsForEmbeddedOptions(convertToDto(tariff, new TariffDto()));
        return tariffDto;
    }
    @Transactional
    public String merge(Tariff tariff, String[] optionId) {
        String result = "change failed";
        try {
            Set<Option> optionSet = new HashSet<>();
            if (optionId != null && optionId.length > 0) {
                for (String id : optionId) {
                    Option option = optionDao.findById(Integer.parseInt(id));
                    optionSet.add(option);
                }
            }
            tariff.setOptions(optionSet);
            tariffDao.update(tariff);
            result = "changes successful";
        } catch (NumberFormatException e) {
            LOGGER.error("[{}],  merge [{}]  exception = {}", LocalDateTime.now(), LOGGER.getName(), e);
        }
        return result;
    }

    /**
     * Creates a list of required id options for TariffDto.
     *
     * @param tariffDto
     * @return tariffDto</>
     */
    public TariffDto createRequirementsForEmbeddedOptions(DtoEntity tariffDto) {
        TariffDto tariffDtoAlter = (TariffDto) tariffDto;
        for (OptionDto optionDto : tariffDtoAlter.getOptions()) {
            optionDto.setRequirementsId(optionService.setRequirements(optionDto));
            optionDto.setExclusionsId(optionService.setExclusions(optionDto));

        }

        return tariffDtoAlter;
    }

    @Transactional
    @Override
    public TariffDto remove(TariffDto tariffDto, Integer id) {
        Tariff oldTariff = (Tariff) convertToEntity(new Tariff(), tariffDto);
        Tariff newTariff = tariffDao.findById(id);
        List<Contract> contractsWithOldTariff = contractDao.getAllWithOldTariff(oldTariff);
        if (!contractsWithOldTariff.isEmpty()) {
            for (Contract contract : contractsWithOldTariff) {
                for (Option option : contract.getConnectedOptions()) {
                    if (!newTariff.getOptions().contains(option)) {
                        contract.getConnectedOptions().remove(option);
                    }
                }
                contract.setTariff(newTariff);
                contractDao.update(contract);
            }
        }
        oldTariff.setDeleted(true);
        tariffDao.update(oldTariff);
        return (TariffDto) convertToDto(newTariff, new TariffDto());
    }


}
