package com.stressthem.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/users/login", "/users/register", "/index","/")
                .anonymous()
                .antMatchers("/admin/**","/plans/delete/**","/currencies/delete/**","/articles/delete/**").hasAnyAuthority("ADMIN", "ROOT")
                .antMatchers("/plans", "/articles", "/faq", "/contact", "/currencies").permitAll()
                .antMatchers("/home/**","/plans/**").authenticated()
                .and()
                .formLogin().loginPage("/users/login").loginProcessingUrl("/users/login")
                .defaultSuccessUrl("/home/launch").failureUrl("/users/login?error")
                .and().logout().logoutUrl("/users/logout").invalidateHttpSession(true).logoutSuccessUrl("/index").
                and().exceptionHandling().accessDeniedPage("/home/launch");


    }
}
