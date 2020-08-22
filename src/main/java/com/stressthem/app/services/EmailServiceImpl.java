package com.stressthem.app.services;

import com.stressthem.app.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender sender;

    @Autowired
    public EmailServiceImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendConfirmationEmail() {

    }
}
