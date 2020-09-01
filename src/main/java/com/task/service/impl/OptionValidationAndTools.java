package com.task.service.impl;

import com.task.dao.OptionDao;
import com.task.dto.OptionDto;
import com.task.entity.Option;
import com.task.exception.WrongOptionException;
import com.task.service.GenericMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@Log4j
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class OptionValidationAndTools extends GenericMapper {


    private OptionDao optionDao;

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

    public void checkOptionName(OptionDto optionDto) {
        if (optionDto.getId() == null) {
            if (optionDao.findByName(optionDto.getName()) != null)
                throw new WrongOptionException("Option with this name already exist", optionDto);
        } else {
            Option option = optionDao.findByName(optionDto.getName());
            if (option.getId() != optionDto.getId())
                throw new WrongOptionException("Option with this name already exist", optionDto);
        }
    }

    public List<OptionDto> createOnlyExclusionOptions(Set<OptionDto> chooseRequiredOptions,
                                                       List<OptionDto> onlyExclusionOptions) {

        if (chooseRequiredOptions.size() > 0) {
            for (OptionDto reqOption : chooseRequiredOptions) {
                if (reqOption.getRequiredOptions().size() > 0)
                    createOnlyExclusionOptions(reqOption.getRequiredOptions(), onlyExclusionOptions);
                onlyExclusionOptions.addAll(reqOption.getExclusionOptions());
            }
        }
        return onlyExclusionOptions;
    }


    public void createReqHierarchy(List<OptionDto> allOptions, Set<OptionDto> chooseRequiredOptions) {
        for (OptionDto optionDto : chooseRequiredOptions) {
            if (optionDto.getRequiredOptions().size() > 0)
                createReqHierarchy(allOptions, optionDto.getRequiredOptions());
            allOptions.add(optionDto);
        }
    }

    public void createExclHierarchy(List<OptionDto> allOptions, Set<OptionDto> chooseExclusionOptions) {
        for (OptionDto optionDto : chooseExclusionOptions) {
            if (optionDto.getExclusionOptions().size() > 0)
                createExclHierarchy(allOptions, optionDto.getExclusionOptions());
            else if (!allOptions.contains(optionDto)) allOptions.add(optionDto);
        }
    }


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
            OptionDto optionDtoFromChild = queueChildOptions.pollFirst();
            if (allOptions.contains(optionDtoFromChild)) {
                throw new WrongOptionException(optionDtoFromChild.getName() +
                        "  violates the principle of binding to the selected options", createOption);
            }
            if (optionDtoFromChild.getRequiredOptions().size() > 0) {
                queueChildOptions.addAll(optionDtoFromChild.getRequiredOptions());
            } else if (optionDtoFromChild.getExclusionOptions().size() > 0) {
                queueChildOptions.addAll(optionDtoFromChild.getExclusionOptions());
            }
        }
    }

    public void checkLikeChild(List<OptionDto> allCheckingOptions, OptionDto optionDto, OptionDto createOption,
                               List<OptionDto> onlyExclusionOptions) {
        List<OptionDto> parentOptions = getAllParentDto(optionDto);
        if (parentOptions != null) {
            for (OptionDto parentDto : parentOptions) {
                if (allCheckingOptions.contains(parentDto))
                    throw new WrongOptionException("Chosen options violates the principle of " +
                            "coupling with option " + parentDto.getName(), createOption);
                if (parentDto.getExclusionOptions().size() > 0) {
                    for (OptionDto exclOptDtoParent : parentDto.getExclusionOptions()) {
                        if (allCheckingOptions.contains(exclOptDtoParent))
                            throw new WrongOptionException("Chosen options violates the principle of " +
                                    "coupling with option " + parentDto.getName(), createOption);
                    }
                } else if (parentDto.getRequiredOptions().size() > 0) {
                    for (OptionDto reqOptDtoParent : parentDto.getRequiredOptions()) {
                        if (reqOptDtoParent != optionDto && onlyExclusionOptions.contains(reqOptDtoParent)) {
                            throw new WrongOptionException("Chosen options violates the principle of " +
                                    "coupling with option " + reqOptDtoParent.getName(), createOption);
                        }
                    }
                }
                checkLikeChild(allCheckingOptions, parentDto, createOption, onlyExclusionOptions);
            }
        }
    }

    public List<OptionDto> getAllParentDto(OptionDto optionDto) {
        List<Option> parentOptions = optionDao.getAllParent(optionDto.getId());
        if (parentOptions != null) {
            List<OptionDto> parentOptionsDto = parentOptions.stream().map(e -> (OptionDto) convertToDto(e,
                    new OptionDto())).collect(Collectors.toList());
            return parentOptionsDto;
        }
        return null;
    }

    public boolean checkOptions(Set<Option> optionSet) {
        for (Option option : optionSet) {
            if (option.getRequiredOptions().size() > 0) {
                for (Option required : option.getRequiredOptions()) {
                    if (!optionSet.contains(required)) return false;
                }
            }
        }

        return true;
    }

    public void checkContainsRequirementInExclusion(String[] requirement, String[] exclusion, OptionDto optionDto){
        if (requirement != null && exclusion != null && requirement.length > 0 && exclusion.length > 0) {
            for (String req : requirement) {
                if (Arrays.asList(exclusion).contains(req))
                    throw new WrongOptionException("Option cannot be both in requirements and exceptions", optionDto);
            }
        }
    }

    /**
     * Create constraints on requirement options and exclusion options.
     * Also it finds any violation in related options.
     * In this case will be thrown WrongOptionException.
     * 2 ways of check:
     * checkLikeParent - checks all inherited options of optionDto.
     * checkLikeChild - find all parent options of optionDto
     *
     * @param requirement chosen options by user which will have to be connected before option - optionDto
     * @param exclusion   chosen options by user which can not be connected with option - optionDto
     * @param optionDto   editable or new option
     * @return String result
     */
    public OptionDto globalCheck(String[] requirement, String[] exclusion, OptionDto optionDto) {
        checkOptionName(optionDto);
        checkContainsRequirementInExclusion(requirement,  exclusion, optionDto);
        Set<OptionDto> chooseRequiredOptions = new HashSet<>();
        Set<OptionDto> chooseExclusionOptions = new HashSet<>();
        if (requirement != null && requirement.length > 0) {
            for (String idReq : requirement) {
                OptionDto optionReqDto = (OptionDto) convertToDto(optionDao.findById(Integer.parseInt(idReq)),
                        new OptionDto());
                chooseRequiredOptions.add(optionReqDto);
            }
        }
        if (exclusion != null && exclusion.length > 0) {
            for (String idExcl : exclusion) {
                OptionDto optionExDto = (OptionDto) convertToDto(optionDao.findById(Integer.parseInt(idExcl)),
                        new OptionDto());
                chooseExclusionOptions.add(optionExDto);
            }
        }
        List<OptionDto> allOptions = new ArrayList<>();
        if (optionDto.getId() != null) allOptions.add(optionDto);
        allOptions.addAll(chooseExclusionOptions);
        if (chooseRequiredOptions.size() > 0) {
            for (OptionDto optionDtoRequired : chooseRequiredOptions) {
                checkLikeParent(allOptions, optionDtoRequired, optionDto);
                allOptions.add(optionDtoRequired);
            }
            createExclHierarchy(allOptions, chooseExclusionOptions);
        }
        createReqHierarchy(allOptions, chooseRequiredOptions);
        if (optionDto.getId() != null) {
            List<OptionDto> onlyExclusionOptions = new ArrayList<>(chooseExclusionOptions);
            onlyExclusionOptions = createOnlyExclusionOptions(chooseRequiredOptions, onlyExclusionOptions);
            checkLikeChild(allOptions, optionDto, optionDto, onlyExclusionOptions);
        }
        optionDto.setRequiredOptions(chooseRequiredOptions);
        optionDto.setExclusionOptions(chooseExclusionOptions);
        return optionDto;
    }
}
