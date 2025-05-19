package com.aieta.springboot.todo_app.infrastructure.persistance.user;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.aieta.springboot.todo_app.domain.model.user.User;

public interface MongoUserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
