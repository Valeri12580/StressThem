package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.entities.Action;
import com.stressthem.app.domain.models.service.UserServiceModel;

import java.time.LocalDateTime;

public interface LoggerService {

    void log(Action action, String description, UserServiceModel userServiceModel, LocalDateTime localDateTime);
}
