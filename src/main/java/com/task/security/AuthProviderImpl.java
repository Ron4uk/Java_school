package com.task.security;


import com.task.entity.Contract;
import com.task.service.ContractService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthProviderImpl.class);

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ContractService contractService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phone = authentication.getName().toString();
        Contract contract = contractService.findByPhone(phone);
        LOGGER.info("[{}]  Connect to DB from method findByPhone {}. contract = {}", LocalDateTime.now(), LOGGER.getName(), contract);
        if (contract == null) {
            throw new UsernameNotFoundException("User not found");
        }
        String password = authentication.getCredentials().toString();
        if (!password.equals(contract.getAuth().getPassword())) {
            throw new BadCredentialsException("wrong password");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(contract.getAuth().getRoles().iterator().next().toString()));

        UsernamePasswordAuthenticationToken as = new UsernamePasswordAuthenticationToken(contract, null, authorities);

        return new UsernamePasswordAuthenticationToken(contract, null, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
