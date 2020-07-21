package com.stressthem.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll()
                .and().formLogin().loginPage("/users/login").loginProcessingUrl("/users/login")
                .defaultSuccessUrl("/home/launch").failureUrl("/users/login?error")
                .and().logout().logoutUrl("/users/logout").invalidateHttpSession(true).logoutSuccessUrl("/index");

    }
}
