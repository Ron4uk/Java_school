package com.task.service.impl;

import com.task.dao.ContractDao;
import com.task.dao.OptionDao;
import com.task.dao.TariffDao;
import com.task.dto.DtoEntity;
import com.task.dto.OptionDto;
import com.task.dto.TariffDto;
import com.task.entity.Contract;
import com.task.entity.Option;
import com.task.entity.Tariff;
import com.task.service.GenericMapper;
import com.task.service.OptionService;
import com.task.service.TariffService;
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
public class TariffServiceImpl extends GenericMapper implements TariffService {
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TariffDao tariffDao;
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private OptionDao optionDao;
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private OptionService optionService;
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ContractDao contractDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(TariffServiceImpl.class);

    public List<Tariff> getAll() {
        return tariffDao.getAll();
    }

    public List<DtoEntity> getAllDto() {

        return tariffDao.getAll().stream().map(e -> this.convertToDto(e, new TariffDto())).collect(Collectors.toList());
    }

    @Override
    public List<TariffDto> getAllDtoWithReq() {
        List<TariffDto> tariffDtoList = tariffDao.getAll().stream().map(e -> (TariffDto) this.convertToDto(e, new TariffDto())).collect(Collectors.toList());
        tariffDtoList.forEach(e -> createRequirementsForEmbeddedOptions(e));
        return tariffDtoList;
    }

    @Override
    public List<TariffDto> getAllWithoutDto(Integer id) {

        return tariffDao.getAllWithout(id).stream().map(e -> (TariffDto) this.convertToDto(e, new TariffDto())).collect(Collectors.toList());
    }

    public Tariff create(Tariff tariff) {
        return tariffDao.create(tariff);
    }

    public Tariff findById(Integer id) {
        return tariffDao.findById(id);
    }

    @Override
    public TariffDto findByIdDto(Integer id) {
        Tariff tariff = tariffDao.findById(id);
        TariffDto tariffDto = (TariffDto) createRequirementsForEmbeddedOptions(convertToDto(tariff, new TariffDto()));
        return tariffDto;
    }

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


    @Override
    public TariffDto remove(TariffDto tariffDto, Integer id) {
        Tariff oldTariff = (Tariff) convertToEntity(new Tariff(), tariffDto);
        Tariff newTariff = tariffDao.findById(id);
        LOGGER.info("[{}],  get all contracts with old Tariff [{}]  oldTariff = {}", LocalDateTime.now(), LOGGER.getName(), oldTariff);
        List<Contract> contractsWithOldTariff = contractDao.getAllWithOldTariff(oldTariff);
        if (!contractsWithOldTariff.isEmpty()) {
            for (Contract contract : contractsWithOldTariff) {
                contract.setConnectedOptions(new HashSet<>());
                contract.setTariff(newTariff);
                contractDao.update(contract);
            }
        }

        LOGGER.info("[{}],  remove [{}]  oldTariff = {}", LocalDateTime.now(), LOGGER.getName(), oldTariff);
        tariffDao.deleteById(oldTariff.getId());
        return (TariffDto) convertToDto(newTariff, new TariffDto());
    }
}
