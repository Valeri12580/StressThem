package com.stressthem.app.exceptions;

public class DuplicatedEmailException extends RuntimeException
{
    public DuplicatedEmailException(String message) {
        super(message);
    }
}
