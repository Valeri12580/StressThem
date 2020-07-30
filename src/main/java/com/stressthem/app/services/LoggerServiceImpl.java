package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Action;
import com.stressthem.app.domain.entities.Logger;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.repositories.LoggerRepository;
import com.stressthem.app.services.interfaces.LoggerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoggerServiceImpl implements LoggerService {
    private ModelMapper modelMapper;
    private LoggerRepository loggerRepository;

    @Autowired
    public LoggerServiceImpl(ModelMapper modelMapper, LoggerRepository loggerRepository) {
        this.modelMapper = modelMapper;
        this.loggerRepository = loggerRepository;
    }

    @Override
    public void log(Action action, String description, UserServiceModel userServiceModel
            , LocalDateTime localDateTime) {

        User user=this.modelMapper.map(userServiceModel,User.class);
        Logger logger=new Logger(action,description,user,localDateTime);
        this.loggerRepository.save(logger);

    }
}
