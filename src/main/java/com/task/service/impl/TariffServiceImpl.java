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
import com.task.service.TariffSender;
import com.task.service.TariffService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private TariffSender tariffSender;
    private OptionValidationAndTools validationAndTools;
    private JmsTemplate jmsTemplate;

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
        List<TariffDto> tariffDtoList = tariffDao.getAll().stream().map(e ->
                (TariffDto) this.convertToDto(e, new TariffDto())).collect(Collectors.toList());
        tariffDtoList.forEach(this::createRequirementsForEmbeddedOptions);
        return tariffDtoList;
    }

    @Transactional
    @Override
    public List<TariffDto> getAllWithoutDto(Integer id) {
        List<TariffDto> tariffDtoList = tariffDao.getAllWithout(id).stream().map(e ->
                (TariffDto) this.convertToDto(e, new TariffDto())).collect(Collectors.toList());
        tariffDtoList.forEach(this::createRequirementsForEmbeddedOptions);
        return tariffDtoList;
    }


    @Transactional
    public Tariff findById(Integer id) {
        return tariffDao.findById(id);
    }

    @Transactional
    @Override
    public TariffDto findByIdDto(Integer id) {
        Tariff tariff = tariffDao.findById(id);
        return (TariffDto) createRequirementsForEmbeddedOptions(convertToDto(tariff, new TariffDto()));
    }

    @Transactional
    public String merge(TariffDto tariffDto, String[] optionId) {

        Tariff tariff = (Tariff) convertToEntity(new Tariff(), tariffDto);
        Set<Option> optionSet = new HashSet<>();
        for (OptionDto optionDto : tariffDto.getOptions()) {
            if (optionDto.getDeleted()) {
                Option option = (Option) convertToEntity(new Option(), optionDto);
                tariff.getOptions().add(option);
            }
        }
        if (optionId != null && optionId.length > 0) {
            tariffDto.setOptions(new HashSet<>());
            tariff.setOptions(new HashSet<>());
            for (String id : optionId) {
                Option option = optionDao.findById(Integer.parseInt(id));
                optionSet.add(option);
                OptionDto optionDto = (OptionDto) convertToDto(option, new OptionDto());
                tariffDto.getOptions().add(optionDto);
                tariff.getOptions().add(option);
            }
            if (!validationAndTools.checkOptions(optionSet)) return "Changes failed. Check requirements";
        }
        tariff.getOptions().addAll(optionSet);
        Tariff updatedTariff = tariffDao.update(tariff);
        tariffDto.setId(updatedTariff.getId());
        sendTariffsToQueue();
        return "changes successful";
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
            optionDto.setRequirementsId(validationAndTools.setRequirements(optionDto));
            optionDto.setExclusionsId(validationAndTools.setExclusions(optionDto));

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
                contract.getConnectedOptions().removeIf(option -> !newTariff.getOptions().contains(option));
                contract.setTariff(newTariff);
                contractDao.update(contract);
            }
        }
        oldTariff.setDeleted(true);
        tariffDao.update(oldTariff);
        sendTariffsToQueue();
        return (TariffDto) convertToDto(newTariff, new TariffDto());
    }


    @Override
    public void sendTariffsToQueue() {
        HashSet<TariffDto> tariffDtos = new HashSet<>(tariffDao.getAll().stream().
                map(e -> (TariffDto) convertToDto(e, new TariffDto())).collect(Collectors.toSet()));
        tariffSender.sendTariffs(tariffDtos);
        LOGGER.info("message sent to queue");

    }
}
