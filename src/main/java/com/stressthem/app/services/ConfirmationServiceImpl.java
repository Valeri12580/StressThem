package com.stressthem.app.services;

import com.stressthem.app.helpers.UserConfirmationCode;
import com.stressthem.app.services.interfaces.ConfirmationService;
import com.stressthem.app.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {
    private EmailService emailService;
    private UserConfirmationCode userConfirmationCode;

    @Autowired
    public ConfirmationServiceImpl(EmailService emailService, UserConfirmationCode userConfirmationCode) {
        this.emailService = emailService;
        this.userConfirmationCode = userConfirmationCode;
    }

    @Override
    public String sendConfirmationEmail(String to) {
        String uuid= UUID.randomUUID().toString();
        emailService.sendConfirmationEmail(to,"Confirm your account | StressThem",
                String.format("Please confirm your account to get full access to our features.." +
                        "Code -> %s",uuid));

       userConfirmationCode= userConfirmationCode.setCode(uuid);
        return uuid;
    }

    @Override
    public boolean confirmConfirmationCode(String code) {
        return code.equals(userConfirmationCode.getCode());
    }
}
