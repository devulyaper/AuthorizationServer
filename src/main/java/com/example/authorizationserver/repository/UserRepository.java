package com.example.authorizationserver.repository;

import com.example.authorizationserver.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
   Optional<User> findByEmail(String email);
}
