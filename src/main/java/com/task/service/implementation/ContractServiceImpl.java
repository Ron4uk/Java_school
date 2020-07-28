package com.task.service.implementation;

import com.task.dao.ContractDao;
import com.task.entity.Contract;
import com.task.service.ContractService;
import com.task.service.GenericMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContractServiceImpl extends GenericMapper implements ContractService {
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ContractDao contractDAO;

    public Contract findByPhone(String phone) {
        return contractDAO.findByPhone(phone);
    }
}
