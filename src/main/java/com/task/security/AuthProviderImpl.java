package com.task.security;


import com.task.dao.AuthorizationDao;
import com.task.entity.Authorization;
import com.task.service.AuthorizationServiceImpl;
import com.task.service.ClientServiceImpl;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);
    private AuthorizationServiceImpl authorizationService;

    public AuthorizationServiceImpl getAuthorizationService() {
        return authorizationService;
    }

    @Autowired
    public void setAuthorizationService(AuthorizationServiceImpl authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phone = authentication.getName().toString();
        LOGGER.info("[{}]  phone = {}", LocalDateTime.now(), phone);
        Authorization authorization = authorizationService.findByPhone(phone);
        LOGGER.info("[{}]  Connect to DB from method findByPhone {}. authorization = {}", LocalDateTime.now(), LOGGER.getName(), authorization);
        if(authorization==null){
            throw new UsernameNotFoundException("User not found");
        }
        String password = authentication.getCredentials().toString();
        if(!password.equals(authorization.getPassword())){
            throw new BadCredentialsException("wrong password");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new UsernamePasswordAuthenticationToken(authorization, null,authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
