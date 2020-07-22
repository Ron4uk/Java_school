package com.task.service;

import com.task.dao.ContractDao;
import com.task.entity.Contract;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContractServiceImpl extends GenericMapper {
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ContractDao contractDAO;

    public Contract findByPhone(String phone) {
        return contractDAO.findByPhone(phone);
    }
}
