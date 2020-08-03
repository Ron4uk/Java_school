package com.task.service.implementation;

import com.task.dao.OptionDao;
import com.task.dto.DtoEntity;
import com.task.dto.OptionDto;
import com.task.entity.Option;
import com.task.service.GenericMapper;
import com.task.service.OptionService;
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
public class OptionServiceImpl extends GenericMapper implements OptionService {
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

    public List<OptionDto> getAllDtoWithReqId() {
        List<OptionDto> result = optionDao.getAll().stream().map(e -> (OptionDto) this.convertToDto(e, new OptionDto())).collect(Collectors.toList());
        for (OptionDto optionDto : result) {
            Set<Integer> requirementsId = setRequirements(optionDto);
            Set<Integer> exclusionId = setExclusions(optionDto);
            optionDto.setExclusionsId(exclusionId);
            optionDto.setRequirementsId(requirementsId);

        }
        return result;
    }

    /**
     * Creates a list of required id options for processing it in jsp (these id will be selected on the checkboxes).
     *
     * @param optionDto
     * @return Set<Integer> requirementsId</>
     */
    public Set<Integer> setRequirements(OptionDto optionDto) {
        Set<Integer> requirementsId = new HashSet<>();
        if (optionDto.getRequiredOptions().size() > 0) {
            for (OptionDto optionDtoNext : optionDto.getRequiredOptions()) {
                requirementsId.addAll(setRequirements(optionDtoNext));
                requirementsId.add(optionDtoNext.getId());
            }

        }
        return requirementsId;
    }
    /**
     * Creates a list of excluded id options for processing it in jsp (these id will be excluded on the checkboxes).
     *
     * @param optionDto
     * @return Set<Integer> requirementsId</>
     */
    @Override
    public Set<Integer> setExclusions(OptionDto optionDto) {
        Set<Integer> exclusionsId = new HashSet<>();
        if (optionDto.getExclusionOptions().size() > 0) {
            for (OptionDto optionDtoNext : optionDto.getExclusionOptions()) {
                exclusionsId.addAll(setExclusions(optionDtoNext));
                exclusionsId.add(optionDtoNext.getId());
            }

        }
        return exclusionsId;
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

    public String update(String[] requirement, String[] exclusion, OptionDto optionDto) {
        String result = "change failed.";
        try {
            result += createOptionConstraint(requirement, exclusion, optionDto);
            LOGGER.info("[{}],  update [{}]  result = {}", LocalDateTime.now(), LOGGER.getName(), result);
            if (!result.equals("change failed.")) return result;
            optionDao.update((Option) convertToEntity(new Option(), optionDto));
            result = "changes successful.";
        } catch (Exception e) {
            LOGGER.error("[{}],  update [{}]  exception = {}", LocalDateTime.now(), LOGGER.getName(), e);
            e.printStackTrace();

        }
        return result;
    }

//TODO make it easier. Maybe can use ControllerAdvice?

    /**
     * Create constraints on requirement options and exclusion options.
     * Also it checks  have any options from exclusion options in inherited options of requirement options.
     * In this case option won't be updated and return the name and id of option wich have to remove from the Set of exclusion options.
     *
     * @param requirement
     * @param exclusion
     * @param optionDto
     * @return String result (id and name options)
     */
    public String createOptionConstraint(String[] requirement, String[] exclusion, OptionDto optionDto) {

        Set<OptionDto> requiredOptions = new HashSet<>();
        Set<OptionDto> exclusionOptions = new HashSet<>();
        String result = "";
        if (requirement != null && requirement.length > 0) {
            for (String s1 : requirement) {

                OptionDto optionReqDto = (OptionDto) findByIdDto(Integer.parseInt(s1));
                requiredOptions.add(optionReqDto);

            }
            LOGGER.info("[{}],  createOptionConstraint for REQUIREMENT [{}]  requiredOptions = {}", LocalDateTime.now(), LOGGER.getName(), requiredOptions);


        }
        if (exclusion != null && exclusion.length > 0) {

            for (String s2 : exclusion) {
                OptionDto optionExDto = (OptionDto) findByIdDto(Integer.parseInt(s2));
                exclusionOptions.add(optionExDto);
            }
            LOGGER.info("[{}],  createOptionConstraint for EXCLUSION [{}]  exclusionOptions = {}", LocalDateTime.now(), LOGGER.getName(), exclusionOptions);

        }
        for (OptionDto opt : exclusionOptions) {
            result += checkChildsReqORExclOnContainsExcl(requiredOptions, opt);
            LOGGER.info("[{}],  checkChildsReqORExclOnContainsExcl  result = {}", LocalDateTime.now(), result);
        }

        optionDto.setRequiredOptions(requiredOptions);
        optionDto.setExclusionOptions(exclusionOptions);
        return result;
    }

    public String checkChildsReqORExclOnContainsExcl(Set<OptionDto> optionDtoSet, OptionDto optionDto) {
        String result = "";
        for (OptionDto opt : optionDtoSet) {
            if (opt.getRequiredOptions().size() > 0) {
                result += checkChildsReqORExclOnContainsExcl(opt.getRequiredOptions(), optionDto);
            }
            if (opt.getExclusionOptions().size() > 0) {
                result += checkChildsReqORExclOnContainsExcl(opt.getExclusionOptions(), optionDto);
            }
            result += opt.getId() == optionDto.getId() ? (" id " + optionDto.getId() + " " + optionDto.getName() + " is contained in the expansion of requirement options. Please change you choice.\n") : "";

        }
        return result;
    }


    public String deleteById(Integer id) {
        String result = "change failed.";
        try {
            LOGGER.info("[{}],  deleteById [{}] Option id = {}", LocalDateTime.now(), LOGGER.getName(), id);
            optionDao.deleteById(id);
            result = "changes successful.";
        } catch (Exception e) {
            LOGGER.error("[{}],  delete [{}]  exception = {}", LocalDateTime.now(), LOGGER.getName(), e);
            e.printStackTrace();

        }
        return result;
    }
}
