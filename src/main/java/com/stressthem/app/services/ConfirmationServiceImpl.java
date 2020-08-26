package com.stressthem.app.services;

import com.stressthem.app.services.interfaces.ConfirmationService;
import com.stressthem.app.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {
    private EmailService emailService;

    @Autowired
    public ConfirmationServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public String sendConfirmationEmail(String to) {
        String uuid= UUID.randomUUID().toString();
        emailService.sendConfirmationEmail(to,"Confirm your account | StressThem",
                String.format("Please confirm your account to get full access to our features.." +
                        "Code -> %s",uuid));

        return uuid;
    }
}
