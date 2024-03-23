package com.example.authorizationserver.service;

import com.example.authorizationserver.dto.UserDTO;
import com.example.authorizationserver.entity.Role;
import com.example.authorizationserver.entity.User;
import com.example.authorizationserver.exception.LoginException;
import com.example.authorizationserver.exception.RegistrationException;
import com.example.authorizationserver.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found."));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getHashPassword())
                .roles(user.getRole().toString())
                .build();
    }

    public void register(UserDTO registrationUserDto) {
        if (registrationUserDto.getEmail() == null || registrationUserDto.getPassword() == null) {
            throw new RegistrationException("Email and password cannot be null");
        }

        if(userRepository.findByEmail(registrationUserDto.getEmail()).isPresent()) {
            throw new RegistrationException(
                    "Client with email: " + registrationUserDto.getEmail() + " already registered");
        }
        User user = new User(
            UUID.randomUUID(),
            registrationUserDto.getEmail(),
            passwordEncoder.encode(registrationUserDto.getPassword()),
            registrationUserDto.getName(),
            registrationUserDto.getBirthDate(),
            registrationUserDto.getGender(),
            Role.USER);
        userRepository.save(user);
    }

    public void checkCredentials(String email, String password) {
        Optional<User> optionalUserEntity = userRepository
                .findByEmail(email);
        if (optionalUserEntity.isEmpty()) {
            throw new LoginException(
                    "Client with email: " + email + " not found");
        }

        User user = optionalUserEntity.get();
        if(!passwordEncoder.matches(password, user.getHashPassword())){
            throw new LoginException("Password is incorrect");
        }
    }
}
