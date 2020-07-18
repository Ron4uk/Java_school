package com.task.config;

import com.task.security.AuthProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
@ComponentScan("com.task.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private AuthProviderImpl authProvider;

    public AuthProviderImpl getAuthProvider() {
        return authProvider;
    }
    @Autowired
    public void setAuthProvider(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/startpage", "/").anonymous()
                .antMatchers("/client", "/startauthclient").authenticated()
                .and().csrf().disable()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/client")
                .usernameParameter("phone")
                .failureUrl("/login?error=true")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/startauthclient")
                .and().logout().logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
}
