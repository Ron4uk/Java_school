package com.task.config.handlers;

import com.task.entity.Contract;
import com.task.security.AuthProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

@Configuration
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        Integer id= ((Contract) authentication.getPrincipal()).getId();

        LOGGER.info("[{}]  roles = {}, role.contains.client={}  authentication.getName()={} ", LocalDateTime.now(), roles, roles.contains("CLIENT"),authentication.getName());
        if (roles.contains("CLIENT")) {
            httpServletResponse.sendRedirect("/user?id="+id);
        } else {
            httpServletResponse.sendRedirect("/employee");
        }
    }
}
