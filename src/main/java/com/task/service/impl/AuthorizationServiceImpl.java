package com.task.service.impl;


import com.task.dao.AuthorizationDao;
import com.task.service.GenericMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter(onMethod = @__({@Autowired}))
public class AuthorizationServiceImpl extends GenericMapper {

    private AuthorizationDao authorizationDao;


}
