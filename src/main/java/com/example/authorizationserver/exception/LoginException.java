package com.example.authorizationserver.exception;

public class LoginException extends RuntimeException{
    public LoginException(String message) {
        super(message);
    }
}
