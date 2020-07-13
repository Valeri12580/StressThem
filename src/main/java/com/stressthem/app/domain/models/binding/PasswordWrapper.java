package com.stressthem.app.domain.models.binding;


import com.stressthem.app.validation.PasswordMatch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@PasswordMatch(message = "Passwords are not equal!")
public class PasswordWrapper {

    @Size(min = 8,max = 30,message = "Password length must be minimum 8 characters and maximum 30 characters!")
    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;
}
