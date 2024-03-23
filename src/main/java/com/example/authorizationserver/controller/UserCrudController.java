package com.example.authorizationserver.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
public class UserCrudController {
    @GetMapping("/users/{userId}")
    @ResponseBody
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ROLE_ADMIN')")
    public String getUserById(@PathVariable("userId") UUID userId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UUID currentUserId = Long.parseLong(authentication.getName());
//
//        if (currentUserId.equals(userId)) {
//            return "Information about the current user";
//        } else {
//            return "Information about user with ID: " + userId;
//        }
        return null;
    }
}
