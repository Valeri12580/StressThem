package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.models.service.PlanServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;

public interface UserService {

    UserServiceModel register(UserServiceModel userServiceModel);

    UserServiceModel findUserById(String id);

    UserServiceModel getUserByUsername(String username);
    UserServiceModel getUserByEmail(String email);
    long getUsersCount();
    boolean hasUserActivePlan(String username);

    UserServiceModel purchasePlan(String id,String username);

    int getUserAvailableAttacks(String username);

    UserServiceModel updateUser(UserServiceModel userServiceModel);


    void deleteUser(String id);


}
