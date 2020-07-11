package com.stressthem.app.domain.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor

public class UserRegisterBindingModel {

    @NotNull
    @Size(min = 3,max = 15,message = "Username length must be minimum 2 characters and maximum 15 characters!")
    @Pattern(regexp = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$",message = "The username can contain:alphanumeric characters, underscore and dot,Underscore and dot can't be at the end or start of a username,underscore and dot can't be next to each other ,underscore or dot can't be used multiple times in a row ")
    private String username;

    @Size(min = 3,max = 15,message = "Username length must be minimum 2 characters and maximum 15 characters!")
    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;


    @Email(message = "The email is not valid!")
    private String email;

    @Pattern(regexp = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)",message = "The image url is not valid!")
    private String pictureUrl;
}
