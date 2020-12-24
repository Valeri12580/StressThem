package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService {

    UserServiceModel register(UserServiceModel userServiceModel);

    UserServiceModel findUserById(String id);

    UserServiceModel getUserByUsername(String username);
    UserServiceModel getUserByEmail(String email);
    long getUsersCount();
    boolean hasUserActivePlan(String username);

    UserServiceModel purchasePlan(String id,String username,String cryptocurrency);

    int getUserAvailableAttacks(String username);

    UserServiceModel updateUser(String username, UserServiceModel userServiceModel);

    void changeUserRole(String username, String roleName,String type, String administrator);

    List<UserServiceModel>getAllUsers();

    void deleteUserById(String id);

    void deleteUserByUsername(String username,String currentName);

    void validateUsers(String username, UserServiceModel modified);

    void sendConfirmationEmail(String username);

    boolean confirmConfirmationCode(String code,String username);


}
