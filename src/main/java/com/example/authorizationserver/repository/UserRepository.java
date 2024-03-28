package com.example.authorizationserver.repository;

import com.example.authorizationserver.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
   Optional<User> findByEmail(String email);
}
