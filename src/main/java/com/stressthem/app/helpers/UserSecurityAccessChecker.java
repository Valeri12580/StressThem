package com.stressthem.app.helpers;

import com.stressthem.app.domain.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component("userSecurityAccessChecker")
public class UserSecurityAccessChecker {

    public boolean canAccess(Authentication authentication, String userId){

        User user=(User)authentication.getPrincipal();

        return user.getId().equals(userId);
    }
}
