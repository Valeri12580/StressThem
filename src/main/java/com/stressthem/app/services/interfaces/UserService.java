package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.models.service.UserServiceModel;

public interface UserService {

    UserServiceModel register(UserServiceModel userServiceModel);

    UserServiceModel getUserByUsername(String username);
    UserServiceModel getUserByEmail(String email);
}
