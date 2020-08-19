package com.stressthem.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserPlanActivationException extends RuntimeException {
    public UserPlanActivationException(String message) {
        super(message);
    }
}
