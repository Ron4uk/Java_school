package com.task.dao;

import com.task.entity.Authorization;

/**
 * Interface DAO layer that represents of Authorization entity.
 */

public interface AuthorizationDao extends GenericDao<Authorization> {

    Authorization findByPhone(String phone);
}
