package com.stressthem.app.services.interfaces;

public interface ConfirmationService {

    String sendConfirmationEmail(String to);

    boolean confirmConfirmationCode(String code);
}
