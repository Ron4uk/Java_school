package com.task.service.impl;

import com.task.exception.WrongOptionException;
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



    /**
     * Create constraints on requirement options and exclusion options.
     * Also it finds any violation in related options.
     * In this case will be thrown WrongOptionException.
     * 2 ways of check:
     * checkLikeParent - checks all inherited options of optionDto.
     * checkLikeChild - find all parent options of optionDto
     *
     * @param requirement
     * chosen options by user which will have to be connected before option - optionDto
     *
     * @param exclusion
     * chosen options by user which can not be connected with option - optionDto
     *
     * @param optionDto
     * editable or new option
     *
     * @return String result
     */

    public String update(String[] requirement, String[] exclusion, OptionDto optionDto) {
        String result = "Changes successful.";
        Set<OptionDto> chooseRequiredOptions = new HashSet<>();
        Set<OptionDto> chooseExclusionOptions = new HashSet<>();
        if (requirement != null && requirement.length > 0) {
            for (String idReq : requirement) {

                OptionDto optionReqDto = (OptionDto) findByIdDto(Integer.parseInt(idReq));
                chooseRequiredOptions.add(optionReqDto);

            }
        }
        if (exclusion != null && exclusion.length > 0) {
            for (String idExcl : exclusion) {
                OptionDto optionExDto = (OptionDto) findByIdDto(Integer.parseInt(idExcl));
                chooseExclusionOptions.add(optionExDto);
            }
         }

            List<OptionDto> allOptions = new ArrayList<>();
            if (optionDto.getId() != null) allOptions.add(optionDto);
            allOptions.addAll(chooseExclusionOptions);
            if( chooseRequiredOptions.size()>0) {
                for (OptionDto optionDtoRequired : chooseRequiredOptions) {
                    checkLikeParent(allOptions, optionDtoRequired, optionDto);
                }
                createExclHierarchy(allOptions, chooseExclusionOptions);
            }
            createReqHierarchy(allOptions, chooseRequiredOptions);
            if (optionDto.getId() != null) {
                List<OptionDto> onlyExclusionOptions = new ArrayList<>();
                onlyExclusionOptions.addAll(chooseExclusionOptions);
                onlyExclusionOptions = createOnlyExclusionOptions(chooseRequiredOptions, onlyExclusionOptions);
                checkLikeChild(allOptions, optionDto, optionDto, onlyExclusionOptions);
            }
        optionDto.setRequiredOptions(chooseRequiredOptions);
        optionDto.setExclusionOptions(chooseExclusionOptions);
        Option option = (Option) convertToEntity(new Option(), optionDto);
        LOGGER.info("[{}],  update  [{}]  option = {}", LocalDateTime.now(), LOGGER.getName(), option);
        optionDao.update((Option) convertToEntity(new Option(), optionDto));
        return result;
    }

    private List<OptionDto> createOnlyExclusionOptions(Set<OptionDto> chooseRequiredOptions, List<OptionDto> onlyExclusionOptions) {

        if(chooseRequiredOptions.size()>0){
            for(OptionDto reqOption:chooseRequiredOptions){
                if(reqOption.getRequiredOptions().size()>0) createOnlyExclusionOptions(reqOption.getRequiredOptions(), onlyExclusionOptions);
                onlyExclusionOptions.addAll(reqOption.getExclusionOptions());
            }
        }
        return onlyExclusionOptions;
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
                throw new WrongOptionException(optionDtoFromChild.getId() + " " + optionDtoFromChild.getName() +
                        "  violates the principle of binding to the selected options", createOption);
            }
            if (optionDtoFromChild.getRequiredOptions().size() > 0) {
                queueChildOptions.addAll(optionDtoFromChild.getRequiredOptions());
            } else if (optionDtoFromChild.getExclusionOptions().size() > 0) {
                queueChildOptions.addAll(optionDtoFromChild.getExclusionOptions());
            }
        }
    }

    public void checkLikeChild(List<OptionDto> allCheckingOptions, OptionDto optionDto, OptionDto createOption, List<OptionDto> onlyExclusionOptions) {
        List<OptionDto> parentOptions = getAllParentDto(optionDto);
        LOGGER.info("[{}],  checkLikeChild for={}  ", LocalDateTime.now(), parentOptions);
        if (parentOptions != null) {
            for (OptionDto parentDto : parentOptions) {
                if (allCheckingOptions.contains(parentDto))
                    throw new WrongOptionException("Chosen options violates the principle of coupling with option "+parentDto.getId() + " " + parentDto.getName(), createOption);
                if(parentDto.getExclusionOptions().size()>0){
                    for(OptionDto exclOptDtoParent: parentDto.getExclusionOptions()){
                        if (allCheckingOptions.contains(exclOptDtoParent))
                            throw new WrongOptionException("Chosen options violates the principle of coupling with option "+parentDto.getId() + " " + parentDto.getName(), createOption);
                    }
                }
                else if(parentDto.getRequiredOptions().size()>0){
                    for(OptionDto reqOptDtoParent: parentDto.getRequiredOptions()){
                        if(reqOptDtoParent!= optionDto && onlyExclusionOptions.contains(reqOptDtoParent)){
                            throw new WrongOptionException("Chosen options violates the principle of coupling with option "+reqOptDtoParent.getId() + " " + reqOptDtoParent.getName(), createOption);
                        }
                    }
                }
                checkLikeChild(allCheckingOptions, parentDto, createOption, onlyExclusionOptions);
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




    public String deleteById(Integer id) {
            LOGGER.info("[{}],  deleteById [{}] Option id = {}", LocalDateTime.now(), LOGGER.getName(), id);
            optionDao.deleteById(id);

        return "Changes successful.";
    }
}
