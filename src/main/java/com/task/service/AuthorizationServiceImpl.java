package com.task.service;


import com.task.dao.AuthorizationDao;
import com.task.entity.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorizationServiceImpl {
    private AuthorizationDao authorizationDao;

    public AuthorizationDao getAuthorizationDao() {
        return authorizationDao;
    }

    @Autowired
    public void setAuthorizationDao(AuthorizationDao authorizationDao) {
        this.authorizationDao = authorizationDao;
    }

    public Authorization findByPhone(String phone){
        return authorizationDao.findByPhone(phone);
    }
}
