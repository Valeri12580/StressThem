package com.stressthem.app.config;

import com.stressthem.app.domain.models.service.AttackServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.domain.models.view.AttackViewModel;
import com.stressthem.app.domain.models.view.ProfileEditViewModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

//        PropertyMap<UserServiceModel, ProfileEditViewModel>editMap= new PropertyMap<>() {
//            @Override
//            protected void configure() {
//
//                map().getPasswordWrapper().setPassword(source.getPassword());
//                map().getPasswordWrapper().setConfirmPassword(source.getPassword());
//            }
//        };


//        PropertyMap<AttackServiceModel, AttackViewModel>myMap=new PropertyMap<AttackServiceModel, AttackViewModel>() {
//            @Override
//            protected void configure() {
//                DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//                map().setExpiresOn(source.getExpiresOn().format(formatter));
//                map().setStatus(source.getExpiresOn().isBefore(LocalDateTime.now(ZoneId.systemDefault()))?"Active":"Inactive");
//            }
//
//
//
//        };

//        mapper.addMappings(myMap);



        return mapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
