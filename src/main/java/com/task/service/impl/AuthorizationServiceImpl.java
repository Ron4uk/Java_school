package com.task.service.impl;


import com.task.dao.AuthorizationDao;
import com.task.service.GenericMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorizationServiceImpl extends GenericMapper {
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AuthorizationDao authorizationDao;




}
