package com.task.service.implementation;

import com.task.dao.OptionDao;
import com.task.dao.TariffDao;
import com.task.dto.DtoEntity;
import com.task.dto.OptionDto;
import com.task.dto.TariffDto;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(TariffServiceImpl.class);

    public List<Tariff> getAll() {
        return tariffDao.getAll();
    }

    public List<DtoEntity> getAllDto() {

        return tariffDao.getAll().stream().map(e -> this.convertToDto(e, new TariffDto())).collect(Collectors.toList());
    }

    public Tariff create(Tariff tariff) {
        return tariffDao.create(tariff);
    }

    public Tariff findById(Integer id) {
        return tariffDao.findById(id);
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
     * Create a list of request id options for TariffDto.
     *
     * @param tariffDto
     * @return tariffDto</>
     */
    public TariffDto createRequirementsForEmbeddedOptions(DtoEntity tariffDto) {
        TariffDto tariffDtoAlter = (TariffDto) tariffDto;
        for (OptionDto optionDto : tariffDtoAlter.getOptions()) {
            optionDto.setRequirementsId(optionService.setrequirements(optionDto));

        }

        return tariffDtoAlter;
    }


}
