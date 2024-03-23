package com.example.authorizationserver.controller;

import com.example.authorizationserver.dto.UserDTO;
import com.example.authorizationserver.dto.ErrorResponse;
import com.example.authorizationserver.exception.LoginException;
import com.example.authorizationserver.exception.RegistrationException;
import com.example.authorizationserver.service.DefaultUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final DefaultUserDetailsService userService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody UserDTO user) {
        userService.register(user);
        return ResponseEntity.ok("Registered");
    }

    @ExceptionHandler({RegistrationException.class, LoginException.class})
    public ResponseEntity<ErrorResponse> handleUserRegistrationException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(ex.getMessage()));
    }
}
