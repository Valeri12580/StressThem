package com.stressthem.app.services;

import com.stressthem.app.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
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
            SimpleMailMessage msg=new SimpleMailMessage();
            msg.setFrom("test-service@abv.bg");
            msg.setTo("valeri125we@gmail.com");
            msg.setSubject("Testing");
            msg.setText("Kole poluchi li");
            sender.send(msg);
    }
}
