package com.example.authorizationserver.service;

public interface TokenService {
    String generateToken(String email);

    boolean checkToken(String token);
}
