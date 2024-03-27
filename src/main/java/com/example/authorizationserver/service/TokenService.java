package com.example.authorizationserver.service;

import com.example.authorizationserver.entity.User;

public interface TokenService {
    String generateToken(User user);

    boolean checkToken(String token);
}
