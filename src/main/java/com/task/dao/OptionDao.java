package com.task.dao;

import com.task.dto.DtoEntity;
import com.task.dto.OptionDto;
import com.task.entity.Option;

import java.util.List;
import java.util.stream.Collectors;

public interface OptionDao extends GenericDao<Option> {
     List<Option> getAllWithout(Integer id);
}
