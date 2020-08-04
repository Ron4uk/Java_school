package com.task.config;

import com.task.security.AuthProviderImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@ComponentScan("com.task.security")
@Getter
@Setter
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthProviderImpl authProvider;
    private AccessDeniedHandler accessDeniedHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/login", "/startpage", "/").anonymous()
                .antMatchers("/client", "/startauthclient").hasAuthority("CLIENT")
                .antMatchers("/employee", "/startauthempl").hasAuthority("EMPL")
                .and().csrf().disable()
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .loginProcessingUrl("/client")
                .usernameParameter("phone")
                .failureUrl("/login?error=true")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and().logout().logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
}
