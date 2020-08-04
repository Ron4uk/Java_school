package com.task.dao.impl;

import com.task.dao.AuthorizationDao;
import com.task.dao.GenericDaoImpl;
import com.task.entity.Authorization;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link AuthorizationDao}
 */

@Repository
public class AuthorizationDaoImpl extends GenericDaoImpl<Authorization> implements AuthorizationDao {

}
