package com.stressthem.app.config;

import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.binding.PasswordWrapper;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.domain.models.view.ProfileEditViewModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper=new ModelMapper();

        PropertyMap<UserServiceModel, ProfileEditViewModel>editMap= new PropertyMap<>() {
            @Override
            protected void configure() {
//                map().setPasswordWrapper(new PasswordWrapper(source.getPassword(), source.getPassword()));
                map().getPasswordWrapper().setPassword(source.getPassword());
                map().getPasswordWrapper().setConfirmPassword(source.getPassword());
            }
        };



        mapper.addMappings(editMap);


        return mapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
