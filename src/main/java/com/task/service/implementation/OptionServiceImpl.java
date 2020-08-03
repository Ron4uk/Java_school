package com.task.service.implementation;

import com.task.customeexceptions.WrongOptionException;
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
import java.util.*;
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
        String result = "Changes successful.";
        Set<OptionDto> chooseRequiredOptions = new HashSet<>();
        Set<OptionDto> chooseExclusionOptions = new HashSet<>();
        if (requirement != null && requirement.length > 0) {
            for (String idReq : requirement) {

                OptionDto optionReqDto = (OptionDto) findByIdDto(Integer.parseInt(idReq));
                chooseRequiredOptions.add(optionReqDto);

            }
            LOGGER.info("[{}],  createOptionConstraint for REQUIREMENT [{}]  chooseRequiredOptions = {}", LocalDateTime.now(), LOGGER.getName(), chooseRequiredOptions);


        }
        if (exclusion != null && exclusion.length > 0) {

            for (String idExcl : exclusion) {
                OptionDto optionExDto = (OptionDto) findByIdDto(Integer.parseInt(idExcl));
                chooseExclusionOptions.add(optionExDto);
            }
            LOGGER.info("[{}],  createOptionConstraint for EXCLUSION [{}]  chooseExclusionOptions = {}", LocalDateTime.now(), LOGGER.getName(), chooseExclusionOptions);

        }

            List<OptionDto> allOptions = new ArrayList<>();
            if (optionDto.getId() != null) allOptions.add(optionDto);
            LOGGER.info("[{}], 0 allOptions = {} optionDto={}", LocalDateTime.now(), allOptions, optionDto);
            allOptions.addAll(chooseExclusionOptions);
            LOGGER.info("[{}], 1 allOptions = {}", LocalDateTime.now(), allOptions);
            if( chooseRequiredOptions.size()>0) {
                for (OptionDto optionDtoRequired : chooseRequiredOptions) {
                    checkLikeParent(allOptions, optionDtoRequired, optionDto);
                }
                createExclHierarchy(allOptions, chooseExclusionOptions);
            }
            LOGGER.info("[{}], 2 allOptions = {}", LocalDateTime.now(), allOptions);

            createReqHierarchy(allOptions, chooseRequiredOptions);
             LOGGER.info("[{}], 3 allOptions = {}", LocalDateTime.now(), allOptions);
            if (optionDto.getId() != null) {
                checkLikeChild(allOptions, optionDto, optionDto);
            }

        optionDto.setRequiredOptions(chooseRequiredOptions);
        optionDto.setExclusionOptions(chooseExclusionOptions);
        Option option = (Option) convertToEntity(new Option(), optionDto);
        LOGGER.info("[{}],  update option [{}]  optionDto = {}", LocalDateTime.now(), LOGGER.getName(), optionDto);
        LOGGER.info("[{}],  update option [{}]  option = {}", LocalDateTime.now(), LOGGER.getName(), option);
        LOGGER.info("  option = {}",  option.getRequiredOptions());
        LOGGER.info("  option = {}",  option.getExclusionOptions());
        optionDao.update((Option) convertToEntity(new Option(), optionDto));
        return result;
    }

    private void createReqHierarchy(List<OptionDto> allOptions, Set<OptionDto> chooseRequiredOptions) {
        for(OptionDto optionDto:chooseRequiredOptions){
            if(optionDto.getRequiredOptions().size()>0) createReqHierarchy(allOptions, optionDto.getRequiredOptions());
             allOptions.add(optionDto);
        }
    }

    private void createExclHierarchy(List<OptionDto> allOptions, Set<OptionDto> chooseExclusionOptions) {
        for(OptionDto optionDto:chooseExclusionOptions){
            if(optionDto.getExclusionOptions().size()>0) createExclHierarchy(allOptions, optionDto.getExclusionOptions());
            else if (!allOptions.contains(optionDto)) allOptions.add(optionDto);
        }
    }


    //TODO i'm doing easy




    public void checkLikeParent(List<OptionDto> allOptionsForCheck, OptionDto optionDto, OptionDto createOption) {
        List<OptionDto> allOptions = new ArrayList<>(allOptionsForCheck);
        allOptions.add(optionDto);
        Deque<OptionDto> queueChildOptions = new ArrayDeque<>();
        if (optionDto.getRequiredOptions().size() > 0) {
            queueChildOptions.addAll(optionDto.getRequiredOptions());
        } else if (optionDto.getExclusionOptions().size() > 0) {
            queueChildOptions.addAll(optionDto.getExclusionOptions());
        }
        while (queueChildOptions.size() > 0) {
            LOGGER.info("[{}], queueChildOptions = {}", LocalDateTime.now(), queueChildOptions);
            OptionDto optionDtoFromChild = queueChildOptions.pollFirst();
            if (allOptions.contains(optionDtoFromChild)) {
                throw new WrongOptionException(optionDtoFromChild.getId() + " " + optionDtoFromChild.getName() +
                        "  violates the principle of binding to the selected options", createOption);
            }
            LOGGER.info("[{}], optionDtoFromChild = {}, optionDtoFromChild.getRequiredOptions().size()={}, optionDtoFromChild.getExclusionOptions().size()={}", LocalDateTime.now(), optionDtoFromChild,
                    optionDtoFromChild.getRequiredOptions().size(), optionDtoFromChild.getExclusionOptions().size());
            if (optionDtoFromChild.getRequiredOptions().size() > 0) {
                queueChildOptions.addAll(optionDtoFromChild.getRequiredOptions());
            } else if (optionDtoFromChild.getExclusionOptions().size() > 0) {
                queueChildOptions.addAll(optionDtoFromChild.getExclusionOptions());
            }
        }
        LOGGER.info("checkLikeParent EXIT");
    }

    public void checkLikeChild(List<OptionDto> allCheckingOptions, OptionDto optionDto, OptionDto createOption) {
        List<OptionDto> parentOptions = getAllParentDto(optionDto);
        LOGGER.info("[{}],  allCheckingOptions = {}", LocalDateTime.now(), allCheckingOptions);
        if (parentOptions != null) {
            for (OptionDto parentDto : parentOptions) {
                LOGGER.info("[{}],  allCheckingOptions = {}, parentDto={} parentDto.getExclusionOptions()={}", LocalDateTime.now(), allCheckingOptions,parentDto, parentDto.getExclusionOptions());
                if (allCheckingOptions.contains(parentDto))
                    throw new WrongOptionException("Chosen options violates the principle of coupling with option "+parentDto.getId() + " " + parentDto.getName(), createOption);
                if(parentDto.getExclusionOptions().size()>0){
                    for(OptionDto exclOptDtoParent: parentDto.getExclusionOptions()){
                        if (allCheckingOptions.contains(exclOptDtoParent))
                            throw new WrongOptionException("Chosen options violates the principle of coupling with option "+parentDto.getId() + " " + parentDto.getName(), createOption);
                    }
                }
                checkLikeChild(allCheckingOptions, parentDto, createOption);
            }
        }
    }

    private List<OptionDto> getAllParentDto(OptionDto optionDto) {
        LOGGER.info("[{}],  getAllParentDto for={}  ", LocalDateTime.now(), optionDto);
        List<Option> parentOptions = optionDao.getAllParent(optionDto.getId());
        if (parentOptions!=null) {
            List<OptionDto> parentOptionsDto = parentOptions.stream().map(e -> (OptionDto) convertToDto(e, new OptionDto())).collect(Collectors.toList());
            return parentOptionsDto;
        }
        return null;
    }
//TODO make it easier. Maybe can use ControllerAdvice?

    /**
     * Create constraints on requirement options and exclusion options.
     * Also it checks  have any options from exclusion options in inherited options of requirement options.
     * In this case option won't be updated and return the name and id of option wich have to remove from the Set of exclusion options.
     *
     * @param
     * @param
     * @param
     * @return String result (id and name options)
     */

    public String deleteById(Integer id) {
            LOGGER.info("[{}],  deleteById [{}] Option id = {}", LocalDateTime.now(), LOGGER.getName(), id);
            optionDao.deleteById(id);

        return "Changes successful.";
    }
}
