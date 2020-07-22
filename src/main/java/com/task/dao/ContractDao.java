package com.task.dao;

import com.task.entity.Contract;

public interface ContractDao extends GenericDao<Contract> {
    Contract findByPhone(String phone);
}
