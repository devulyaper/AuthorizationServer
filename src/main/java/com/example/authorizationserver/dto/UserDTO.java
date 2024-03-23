package com.example.authorizationserver.dto;


import com.example.authorizationserver.entity.Gender;
import lombok.Value;

@Value
public class UserDTO {
    String email;
    String password;
    String name;
    String birthDate;
    Gender gender;
}
