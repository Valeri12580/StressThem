package com.stressthem.app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

//        PropertyMap<UserServiceModel, ProfileEditViewModel>editMap= new PropertyMap<>() {
//            @Override
//            protected void configure() {

//                map().getPasswordWrapper().setPassword(source.getPassword());
//                map().getPasswordWrapper().setConfirmPassword(source.getPassword());
//            }
//        };
//
//
//
//        mapper.addMappings(editMap);


        return mapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
