package com.stressthem.app.services.interfaces;

public interface EmailService {

    void sendConfirmationEmail(String to,String title,String description);
}
