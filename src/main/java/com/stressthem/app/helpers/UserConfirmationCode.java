package com.stressthem.app.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.context.annotation.SessionScope;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConfirmationCode {
    private String code;

    public String getCode() {
        return code;
    }

    public UserConfirmationCode setCode(String code) {
        this.code = code;
        return this;
    }
}
