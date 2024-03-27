package com.example.authorizationserver.controller;

import com.example.authorizationserver.dto.TokenDTO;
import com.example.authorizationserver.dto.UserDTO;
import com.example.authorizationserver.entity.User;
import com.example.authorizationserver.service.DefaultUserDetailsService;
import com.example.authorizationserver.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final TokenService tokenService;
    private final DefaultUserDetailsService userService;

    @PostMapping
    public TokenDTO login(@RequestBody UserDTO userDTO) {
        User user = userService.checkCredentials(userDTO.getEmail(), userDTO.getPassword());

        return new TokenDTO(tokenService.generateToken(user));
    }
}
