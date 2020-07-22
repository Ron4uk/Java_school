package com.task.service;

import com.task.dao.TariffDao;
import com.task.dto.DtoEntity;
import com.task.dto.TariffDto;
import com.task.entity.Tariff;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TariffServiceImpl extends GenericMapper   {
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TariffDao tariffDao;

    public List<Tariff> getAll(){
        return tariffDao.getAll();
    }
    public List<DtoEntity> getAllDto(){
        return tariffDao.getAll().stream().map(e->this.convertToDto(e, new TariffDto())).collect(Collectors.toList());
    }

    public Tariff create(Tariff tariff){
        return tariffDao.create(tariff);
    }

    public Tariff findById(Integer id){
        return tariffDao.findById(id);
    }

    public Tariff merge(Tariff tariff){ return tariffDao.update(tariff);}
}
